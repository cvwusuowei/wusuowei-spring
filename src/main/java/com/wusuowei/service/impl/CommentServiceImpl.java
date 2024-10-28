package com.wusuowei.service.impl;

import com.alibaba.fastjson.JSON;
import com.wusuowei.entity.Article;
import com.wusuowei.entity.Comment;
import com.wusuowei.entity.Talk;
import com.wusuowei.entity.UserInfo;
import com.wusuowei.enums.CommentTypeEnum;
import com.wusuowei.exception.BizException;
import com.wusuowei.listener.GptListener;
import com.wusuowei.mapper.ArticleMapper;
import com.wusuowei.mapper.CommentMapper;
import com.wusuowei.mapper.TalkMapper;
import com.wusuowei.mapper.UserInfoMapper;
import com.wusuowei.model.dto.*;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.vo.CommentVO;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.ReviewVO;
import com.wusuowei.service.AuroraInfoService;
import com.wusuowei.service.CommentService;
import com.wusuowei.util.GptUtil;
import com.wusuowei.util.HTMLUtil;
import com.wusuowei.util.PageUtil;
import com.wusuowei.util.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.wusuowei.constant.BigModelConstant.*;
import static com.wusuowei.constant.CommonConstant.*;
import static com.wusuowei.constant.RabbitMQConstant.EMAIL_EXCHANGE;
import static com.wusuowei.enums.CommentTypeEnum.*;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Value("${website.url}")
    private String websiteUrl;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private AuroraInfoService auroraInfoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final List<Integer> types = new ArrayList<>();


    //初始化评论类型枚举
    @PostConstruct
    public void init() {
        CommentTypeEnum[] values = CommentTypeEnum.values();
        for (CommentTypeEnum value : values) {
            types.add(value.getType());
        }
    }

    /**
     * 保存评论
     * @param commentVO
     * @return  comment.getId();
     */
    @Override
    public Integer saveComment(CommentVO commentVO) {
        checkCommentVO(commentVO);
        WebsiteConfigDTO websiteConfig = auroraInfoService.getWebsiteConfig();
        Integer isCommentReview = websiteConfig.getIsCommentReview();
        commentVO.setCommentContent(HTMLUtil.filter(commentVO.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(UserUtil.getUserDetailsDTO().getUserInfoId())
                .replyUserId(commentVO.getReplyUserId())
                .topicId(commentVO.getTopicId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(isCommentReview == TRUE ? FALSE : TRUE)
                .build();
        commentMapper.insert(comment);
        String fromNickname = UserUtil.getUserDetailsDTO().getNickname();
        if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
            CompletableFuture.runAsync(() -> notice(comment, fromNickname));
        }
        return comment.getId();
    }


    @SneakyThrows
    public void saveCommentGPT(CommentVO commentVO){

        // 新开一个线程存放问题作为评论，提出问题的人的信息就是当前用户的信息，但是默认情况下Spring Security相关的认证信息是绑定到某个线程上的，
        // 也就是说在此线程以外的其它线程上我们无法获取当前登录用户的信息。比如在我们使用@Async来启用一个新的线程的情况下。所以这里提前得到该用户的信息传递给异步任务
        Integer id =  saveComment(commentVO);
        String fromNickname = UserUtil.getUserDetailsDTO().getNickname();
        // 异步保存用户的提问作为评论，并返回生成的id

        WebsiteConfigDTO websiteConfig = auroraInfoService.getWebsiteConfig();
        Integer isCommentReview = websiteConfig.getIsCommentReview();
        String question = commentVO.getCommentContent();
        GptListener gpt = new GptListener();
        // 生成鉴权url
        String authUrl = GptUtil.getAuthUrl(HOSTURL, APIKEY, APISECRET);
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();
        gpt.setWsClosed(false);
        GptListener.setAnswer("");
        // 构造请求头传递给gpt连接监听类
        gpt.setQuestion(GptUtil.processQuestion(question));
        // 向星火模型构造websocket连接
        WebSocket webSocket = client.newWebSocket(request, gpt);
        // 循环等待直到得到所有的答案，连接已经关闭
        while (true){
            Thread.sleep(200);
            if(gpt.getWsClosed()){
                break;
            }
        }
        // 将该答案作为评论的回复存放到数据库之中
        Comment comment = Comment.builder()
                .userId(1) //先把GPT的身份定位自己的身份1
                .replyUserId(UserUtil.getUserDetailsDTO().getUserInfoId()) //GPT回复的对象就是当前发出提问的对象
                .topicId(commentVO.getTopicId())
                .commentContent(GptListener.getAnswer())
                .parentId(id)
                .type(commentVO.getType())
                .isReview(isCommentReview == TRUE ? FALSE : TRUE)
                .build();
        commentMapper.insert(comment);
        if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
            CompletableFuture.runAsync(() -> notice(comment, fromNickname));
        }
    }

   //获取评论
    @Override
    public PageResultDTO<CommentDTO> listComments(CommentVO commentVO) {
        Integer commentCount = commentMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(commentVO.getTopicId()), Comment::getTopicId, commentVO.getTopicId())
                .eq(Comment::getType, commentVO.getType())
                .isNull(Comment::getParentId)
                .eq(Comment::getIsReview, TRUE));
        if (commentCount == 0) {
            return new PageResultDTO<>();
        }
        List<CommentDTO> commentDTOs = commentMapper.listComments(PageUtil.getLimitCurrent(), PageUtil.getSize(), commentVO);
        if (CollectionUtils.isEmpty(commentDTOs)) {
            return new PageResultDTO<>();
        }
        List<Integer> commentIds = commentDTOs.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        List<ReplyDTO> replyDTOS = commentMapper.listReplies(commentIds);
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOS.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        commentDTOs.forEach(item -> item.setReplyDTOs(replyMap.get(item.getId())));
        return new PageResultDTO<>(commentDTOs, commentCount);
    }
    //通过Id获取回复
    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId) {
        return commentMapper.listReplies(Collections.singletonList(commentId));
    }
    //获取前六条评论
    @Override
    public List<CommentDTO> listTopSixComments() {
        return commentMapper.listTopSixComments();
    }


    //管理员页面获取评论
    @SneakyThrows
    @Override
    public PageResultDTO<CommentAdminDTO> listCommentsAdmin(ConditionVO conditionVO) {
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(() -> commentMapper.countComments(conditionVO));
        List<CommentAdminDTO> commentBackDTOList = commentMapper.listCommentsAdmin(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVO);
        return new PageResultDTO<>(commentBackDTOList, asyncCount.get());
    }

    //更新评论
    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        List<Comment> comments = reviewVO.getIds().stream().map(item -> Comment.builder()
                        .id(item)
                        .isReview(reviewVO.getIsReview())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(comments);
    }


    //检查评论类型是否异常
    public void checkCommentVO(CommentVO commentVO) {
        //判断返回的类型值是否正常
        if (!types.contains(commentVO.getType())) {
            throw new BizException("参数校验异常");
        }
        //判断不为空的数据返回的是文章还是说说
        if (Objects.requireNonNull(getCommentEnum(commentVO.getType())) == ARTICLE || Objects.requireNonNull(getCommentEnum(commentVO.getType())) == TALK) {
            //判断标题Id是否为空
            if (Objects.isNull(commentVO.getTopicId())) {
                //为空抛出异常
                throw new BizException("参数校验异常");
            } else {
                //不为空
                //判断请求类型是否为文章
                if (Objects.requireNonNull(getCommentEnum(commentVO.getType())) == ARTICLE) {
                    //获取文章
                    Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>().select(Article::getId, Article::getUserId).eq(Article::getId, commentVO.getTopicId()));
                    //文章不存在
                    if (Objects.isNull(article)) {
                        throw new BizException("参数校验异常");
                    }
                }
                //判断评论类型是否是说说
                if (Objects.requireNonNull(getCommentEnum(commentVO.getType())) == TALK) {
                    //搜索说说
                    Talk talk = talkMapper.selectOne(new LambdaQueryWrapper<Talk>().select(Talk::getId, Talk::getUserId).eq(Talk::getId, commentVO.getTopicId()));
                    //说说不存在
                    if (Objects.isNull(talk)) {
                        throw new BizException("参数校验异常");
                    }
                }
            }
        }
        //查看是否为友链,关于,留言
        if (Objects.requireNonNull(getCommentEnum(commentVO.getType())) == LINK
                || Objects.requireNonNull(getCommentEnum(commentVO.getType())) == ABOUT
                || Objects.requireNonNull(getCommentEnum(commentVO.getType())) == MESSAGE) {
            //获取主题Id
            if (Objects.nonNull(commentVO.getTopicId())) {
                throw new BizException("参数校验异常");
            }
        }

        if (Objects.isNull(commentVO.getParentId())) {
            if (Objects.nonNull(commentVO.getReplyUserId())) {
                throw new BizException("参数校验异常");
            }
        }


        if (Objects.nonNull(commentVO.getParentId())) {
            Comment parentComment = commentMapper.selectOne(new LambdaQueryWrapper<Comment>().select(Comment::getId, Comment::getParentId, Comment::getType).eq(Comment::getId, commentVO.getParentId()));
            if (Objects.isNull(parentComment)) {
                throw new BizException("参数校验异常");
            }
            if (Objects.nonNull(parentComment.getParentId())) {
                throw new BizException("参数校验异常");
            }
            if (!commentVO.getType().equals(parentComment.getType())) {
                throw new BizException("参数校验异常");
            }
            if (Objects.isNull(commentVO.getReplyUserId())) {
                throw new BizException("参数校验异常");
            } else {
                UserInfo existUser = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().select(UserInfo::getId).eq(UserInfo::getId, commentVO.getReplyUserId()));
                if (Objects.isNull(existUser)) {
                    throw new BizException("参数校验异常");
                }
            }
        }
    }


    //评论通知操作
    private void notice(Comment comment, String fromNickname) {
        if (comment.getUserId().equals(comment.getReplyUserId())) {
            if (Objects.nonNull(comment.getParentId())) {
                Comment parentComment = commentMapper.selectById(comment.getParentId());
                if (parentComment.getUserId().equals(comment.getUserId())) {
                    return;
                }
            }
        }
        if (comment.getUserId().equals(BLOGGER_ID) && Objects.isNull(comment.getParentId())) {
            return;
        }

        if (Objects.nonNull(comment.getParentId())) {
            Comment parentComment = commentMapper.selectById(comment.getParentId());
            if (!comment.getReplyUserId().equals(parentComment.getUserId())
                    && !comment.getReplyUserId().equals(comment.getUserId())) {
                UserInfo userInfo = userInfoMapper.selectById(comment.getUserId());
                UserInfo replyUserinfo = userInfoMapper.selectById(comment.getReplyUserId());
                Map<String, Object> map = new HashMap<>();
                String topicId = Objects.nonNull(comment.getTopicId()) ? comment.getTopicId().toString() : "";
                String url = websiteUrl + getCommentPath(comment.getType()) + topicId;
                map.put("content", userInfo.getNickname() + "在" + Objects.requireNonNull(getCommentEnum(comment.getType())).getDesc()
                        + "的评论区@了你，"
                        + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
                EmailDTO emailDTO = EmailDTO.builder()
                        .email(replyUserinfo.getEmail())
                        .subject(MENTION_REMIND)
                        .template("common.html")
                        .commentMap(map)
                        .build();
                rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
            }
            if (comment.getUserId().equals(parentComment.getUserId())) {
                return;
            }
        }
        String title;
        Integer userId = BLOGGER_ID;
        String topicId = Objects.nonNull(comment.getTopicId()) ? comment.getTopicId().toString() : "";
        if (Objects.nonNull(comment.getReplyUserId())) {
            userId = comment.getReplyUserId();
        } else {
            switch (Objects.requireNonNull(getCommentEnum(comment.getType()))) {
                case ARTICLE:
                    userId = articleMapper.selectById(comment.getTopicId()).getUserId();
                    break;
                case TALK:
                    userId = talkMapper.selectById(comment.getTopicId()).getUserId();
                default:
                    break;
            }
        }
        if (Objects.requireNonNull(getCommentEnum(comment.getType())).equals(ARTICLE)) {
            title = articleMapper.selectById(comment.getTopicId()).getArticleTitle();
        } else {
            title = Objects.requireNonNull(getCommentEnum(comment.getType())).getDesc();
        }
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (StringUtils.isNotBlank(userInfo.getEmail())) {
            EmailDTO emailDTO = getEmailDTO(comment, userInfo, fromNickname, topicId, title);
            rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        }
    }

    private EmailDTO getEmailDTO(Comment comment, UserInfo userInfo, String fromNickname, String topicId, String title) {
        EmailDTO emailDTO = new EmailDTO();
        Map<String, Object> map = new HashMap<>();
        if (comment.getIsReview().equals(TRUE)) {
            String url = websiteUrl + getCommentPath(comment.getType()) + topicId;
            if (Objects.isNull(comment.getParentId())) {
                emailDTO.setEmail(userInfo.getEmail());
                emailDTO.setSubject(COMMENT_REMIND);
                emailDTO.setTemplate("owner.html");
                String createTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(comment.getCreateTime());
                map.put("time", createTime);
                map.put("url", url);
                map.put("title", title);
                map.put("nickname", fromNickname);
                map.put("content", comment.getCommentContent());
            } else {
                Comment parentComment = commentMapper.selectOne(new LambdaQueryWrapper<Comment>().select(Comment::getUserId, Comment::getCommentContent, Comment::getCreateTime).eq(Comment::getId, comment.getParentId()));
                if (!userInfo.getId().equals(parentComment.getUserId())) {
                    userInfo = userInfoMapper.selectById(parentComment.getUserId());
                }
                emailDTO.setEmail(userInfo.getEmail());
                emailDTO.setSubject(COMMENT_REMIND);
                emailDTO.setTemplate("user.html");
                map.put("url", url);
                map.put("title", title);
                String createTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(parentComment.getCreateTime());
                map.put("time", createTime);
                map.put("toUser", userInfo.getNickname());
                map.put("fromUser", fromNickname);
                map.put("parentComment", parentComment.getCommentContent());
                if (!comment.getReplyUserId().equals(parentComment.getUserId())) {
                    UserInfo mentionUserInfo = userInfoMapper.selectById(comment.getReplyUserId());
                    if (Objects.nonNull(mentionUserInfo.getWebsite())) {
                        map.put("replyComment", "<a style=\"text-decoration:none;color:#12addb\" href=\""
                                + mentionUserInfo.getWebsite()
                                + "\">@" + mentionUserInfo.getNickname() + " " + "</a>" + parentComment.getCommentContent());
                    } else {
                        map.put("replyComment", "@" + mentionUserInfo.getNickname() + " " + parentComment.getCommentContent());
                    }
                } else {
                    map.put("replyComment", comment.getCommentContent());
                }
            }
        } else {
            String adminEmail = userInfoMapper.selectById(BLOGGER_ID).getEmail();
            emailDTO.setEmail(adminEmail);
            emailDTO.setSubject(CHECK_REMIND);
            emailDTO.setTemplate("common.html");
            map.put("content", "您收到了一条新的回复，请前往后台管理页面审核");
        }
        emailDTO.setCommentMap(map);
        return emailDTO;
    }




}

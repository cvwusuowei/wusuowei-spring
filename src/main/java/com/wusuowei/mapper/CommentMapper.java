package com.wusuowei.mapper;

import com.wusuowei.entity.Comment;
import com.wusuowei.model.dto.CommentAdminDTO;
import com.wusuowei.model.dto.CommentCountDTO;
import com.wusuowei.model.dto.CommentDTO;
import com.wusuowei.model.dto.ReplyDTO;
import com.wusuowei.model.vo.CommentVO;
import com.wusuowei.model.vo.ConditionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    //分页获取评论
    List<CommentDTO> listComments(@Param("current") Long current, @Param("size") Long size, @Param("commentVO") CommentVO commentVO);

    //回复列
    List<ReplyDTO> listReplies(@Param("commentIds") List<Integer> commentIdList);

    //获取前六条评论
    List<CommentDTO> listTopSixComments();

    //评论数量
    Integer countComments(@Param("conditionVO") ConditionVO conditionVO);

    //管理员页面获取评论
    List<CommentAdminDTO> listCommentsAdmin(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

    List<CommentCountDTO> listCommentCountByTypeAndTopicIds(@Param("type") Integer type, @Param("topicIds") List<Integer> topicIds);

    //通过类型和评论主题id获取评论数
    CommentCountDTO listCommentCountByTypeAndTopicId(@Param("type") Integer type, @Param("topicId") Integer topicId);

}

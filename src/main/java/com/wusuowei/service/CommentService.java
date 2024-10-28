package com.wusuowei.service;

import com.wusuowei.entity.Comment;
import com.wusuowei.model.dto.CommentAdminDTO;
import com.wusuowei.model.dto.CommentDTO;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.dto.ReplyDTO;
import com.wusuowei.model.vo.CommentVO;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.ReviewVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CommentService extends IService<Comment> {

    Integer saveComment(CommentVO commentVO);

    PageResultDTO<CommentDTO> listComments(CommentVO commentVO);

    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    List<CommentDTO> listTopSixComments();

    PageResultDTO<CommentAdminDTO> listCommentsAdmin(ConditionVO conditionVO);

    void updateCommentsReview(ReviewVO reviewVO);


    void saveCommentGPT(CommentVO commentVO) throws Exception;
}

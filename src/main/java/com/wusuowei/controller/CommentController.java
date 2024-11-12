package com.wusuowei.controller;

import com.wusuowei.annotation.AccessLimit;
import com.wusuowei.annotation.OptLog;
import com.wusuowei.model.dto.CommentAdminDTO;
import com.wusuowei.model.dto.CommentDTO;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.dto.ReplyDTO;
import com.wusuowei.service.CommentService;
import com.wusuowei.model.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.wusuowei.constant.OptTypeConstant.*;

@Tag(name = "评论模块")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @AccessLimit(seconds = 60, maxCount = 3)
    @OptLog(optType = SAVE)
    @Operation(summary = "添加评论")
    @PostMapping("/comments/save")
    public ResultVO<?> saveComment(@Valid @RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return ResultVO.ok();
    }

    @SneakyThrows
    @AccessLimit(seconds = 60, maxCount = 5)
    @OptLog(optType = SAVE)
    @Operation(summary = "添加GPT问题")
    @PostMapping("/commentsGPT/save")
    public ResultVO<?> saveCommentGPT(@Valid @RequestBody CommentVO commentVO)  {
        commentService.saveCommentGPT(commentVO);
        return ResultVO.ok();
    }

    @Operation(summary = "获取评论")
    @GetMapping("/comments")
    public ResultVO<PageResultDTO<CommentDTO>> getComments(CommentVO commentVO) {
        return ResultVO.ok(commentService.listComments(commentVO));
    }

    @Operation(summary = "根据commentId获取回复")
    @GetMapping("/comments/{commentId}/replies")
    public ResultVO<List<ReplyDTO>> listRepliesByCommentId(@PathVariable("commentId") Integer commentId) {
        return ResultVO.ok(commentService.listRepliesByCommentId(commentId));
    }

    @Operation(summary = "获取前六个评论")
    @GetMapping("/comments/topSix")
    public ResultVO<List<CommentDTO>> listTopSixComments() {
        return ResultVO.ok(commentService.listTopSixComments());
    }

    @Operation(summary = "查询后台评论")
    @GetMapping("/admin/comments")
    public ResultVO<PageResultDTO<CommentAdminDTO>> listCommentBackDTO(ConditionVO conditionVO) {
        return ResultVO.ok(commentService.listCommentsAdmin(conditionVO));
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "审核评论")
    @PutMapping("/admin/comments/review")
    public ResultVO<?> updateCommentsReview(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentsReview(reviewVO);
        return ResultVO.ok();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除评论")
    @DeleteMapping("/admin/comments")
    public ResultVO<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return ResultVO.ok();
    }
}

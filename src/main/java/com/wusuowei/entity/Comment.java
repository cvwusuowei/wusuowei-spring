package com.wusuowei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_comment")
public class Comment {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;                 //评论自增id

    private Integer userId;             //评论用户id

    private Integer replyUserId;         //回复用户id

    private Integer topicId;             //评论主题id

    private String commentContent;        //评论内容

    private Integer parentId;             //父评论id

    private Integer type;                   //评论类型

    private Integer isDelete;

    private Integer isReview;            //是否审核

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}

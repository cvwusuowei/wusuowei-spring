package com.wusuowei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_talk")
public class Talk {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;                          //用户id

    private String content;                           //内容

    private String images;                           //图片

    private Integer isTop;                          //是否置顶

    private Integer status;                        //公开or私密

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
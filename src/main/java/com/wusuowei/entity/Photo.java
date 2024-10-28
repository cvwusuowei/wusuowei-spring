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
@TableName(value = "t_photo")
public class Photo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer albumId;               //相册id

    private String photoName;             //照片名

    private String photoDesc;           //照片描述

    private String photoSrc;           //照片路径

    private Integer isDelete;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
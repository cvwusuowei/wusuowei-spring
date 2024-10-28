package com.wusuowei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_article")
public class Article {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;                  //用户id

    private Integer categoryId;             //分类id

    private String articleCover;            //文章缩略图

    private String articleTitle;           //文章标题

    private String articleContent;        //文章内容

    private Integer isTop;               //是否置顶

    private Integer isFeatured;           //是否推荐

    private Integer isDelete;             //是否删除

    private Integer status;              //状态

    private Integer type;               //文章类型

    private String password;           //访问密码

    private String originalUrl;       //原文链接

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}

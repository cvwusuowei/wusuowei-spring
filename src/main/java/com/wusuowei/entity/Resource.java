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
@TableName("t_resource")
public class Resource {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String resourceName;          //资源目录

    private String url;                  //权限路径

    private String requestMethod;         //请求方法

    private Integer parentId;             //父模块id

    private Integer isAnonymous;          //是否匿名访问

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}

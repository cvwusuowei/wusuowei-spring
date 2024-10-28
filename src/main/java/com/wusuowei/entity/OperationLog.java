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
@TableName("t_operation_log")
public class OperationLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String optModule;             //操作模块

    private String optUri;               //操作url

    private String optType;            //操作类型

    private String optMethod;         //操作方法

    private String optDesc;           //操作描述

    private String requestMethod;        //请求方法

    private String requestParam;      //请求参数

    private String responseData;         //返回数据

    private Integer userId;               //用户id

    private String nickname;              //用户名称

    private String ipAddress;

    private String ipSource;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}

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
@TableName("t_exception_log")
public class ExceptionLog {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String optUri;           //请求接口

    private String optMethod;           //请求方法

    private String requestMethod;          //请求方式

    private String requestParam;            //请求参数

    private String optDesc;               //操作描述

    private String exceptionInfo;         //异常信息

    private String ipAddress;

    private String ipSource;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}

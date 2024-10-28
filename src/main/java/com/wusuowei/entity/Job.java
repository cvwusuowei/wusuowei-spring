package com.wusuowei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_job")
public class Job {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String jobName;             //任务名称

    private String jobGroup;            //任务组名

    private String invokeTarget;        //调用目标字符串

    private String cronExpression;       //cron执行表达式

    private Integer misfirePolicy;       //计划执行错误策略

    private Integer concurrent;           //是否并发执行

    private Integer status;               //状态

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private String remark;                    //备注信息

    @TableField(exist = false)
    private Date nextValidTime;

}

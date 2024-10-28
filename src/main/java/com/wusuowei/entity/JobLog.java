package com.wusuowei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_job_log")
public class JobLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer jobId;               //

    private String jobName;              //任务名称

    private String jobGroup;             //任务组名

    private String invokeTarget;        //调用目标字符串

    private String jobMessage;         //日志信息

    private Integer status;            //状态

    private String exceptionInfo;      //异常信息

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private Date startTime;

    private Date endTime;
}

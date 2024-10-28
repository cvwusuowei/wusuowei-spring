package com.wusuowei.event;

import com.wusuowei.entity.ExceptionLog;
import org.springframework.context.ApplicationEvent;

public class ExceptionLogEvent extends ApplicationEvent {
    public ExceptionLogEvent(ExceptionLog exceptionLog) {
        super(exceptionLog);
    }
}

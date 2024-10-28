package com.wusuowei.event;

import com.wusuowei.entity.OperationLog;
import org.springframework.context.ApplicationEvent;

public class OperationLogEvent extends ApplicationEvent {

    public OperationLogEvent(OperationLog operationLog) {
        super(operationLog);
    }
}

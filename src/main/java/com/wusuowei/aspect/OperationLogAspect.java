package com.wusuowei.aspect;

import com.alibaba.fastjson.JSON;
import com.wusuowei.annotation.OptLog;
import com.wusuowei.entity.OperationLog;
import com.wusuowei.event.OperationLogEvent;
import com.wusuowei.util.IpUtil;
import com.wusuowei.util.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.wusuowei.annotation.OptLog)")
    public void operationLogPointCut() {
    }
    //操作日志保存方法
    @AfterReturning(value = "operationLogPointCut()", returning = "keys")
    @SuppressWarnings("unchecked")
    public void saveOperationLog(JoinPoint joinPoint, Object keys) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes)
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        OperationLog operationLog = new OperationLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 使用 Swagger 3 的 @Tag 注解
        Tag tag = (Tag) signature.getDeclaringType().getAnnotation(Tag.class);
        // 使用 Swagger 3 的 @Operation 注解
        Operation operation = method.getAnnotation(Operation.class);
        OptLog optLog = method.getAnnotation(OptLog.class);

        // 设置模块信息
        if (tag != null && tag.name() != null) {
            operationLog.setOptModule(tag.name());
        }

        // 设置操作类型
        if (optLog != null) {
            operationLog.setOptType(optLog.optType());
        }

        // 设置操作描述
        if (operation != null) {
            operationLog.setOptDesc(operation.summary()); // 使用 summary 或 description
        }

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        methodName = className + "." + methodName;
        operationLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        operationLog.setOptMethod(methodName);

        if (joinPoint.getArgs().length > 0) {
            if (joinPoint.getArgs()[0] instanceof MultipartFile) {
                operationLog.setRequestParam("file");
            } else {
                operationLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
            }
        }
        operationLog.setResponseData(JSON.toJSONString(keys));
        operationLog.setUserId(UserUtil.getUserDetailsDTO().getId());
        operationLog.setNickname(UserUtil.getUserDetailsDTO().getNickname());
        String ipAddress = IpUtil.getIpAddress(request);
        operationLog.setIpAddress(ipAddress);
        operationLog.setIpSource(IpUtil.getIpSource(ipAddress));
        operationLog.setOptUri(request.getRequestURI());

        applicationContext.publishEvent(new OperationLogEvent(operationLog));
    }

}

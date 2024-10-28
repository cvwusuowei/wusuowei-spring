package com.wusuowei.aspect;

import com.alibaba.fastjson.JSON;
import com.wusuowei.entity.ExceptionLog;
import com.wusuowei.event.ExceptionLogEvent;
import com.wusuowei.util.ExceptionUtil;
import com.wusuowei.util.IpUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class ExceptionLogAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("execution(* com.wusuowei.controller..*.*(..))")
    public void exceptionLogPointcut() {
    }

    //异常日志保存方法
    @AfterThrowing(value = "exceptionLogPointcut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Exception e) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        ExceptionLog exceptionLog = new ExceptionLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        //获取请求路径
        exceptionLog.setOptUri(Objects.requireNonNull(request).getRequestURI());
        //获取类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;
        exceptionLog.setOptMethod(methodName);
        exceptionLog.setRequestMethod(Objects.requireNonNull(request).getMethod());

        if (joinPoint.getArgs().length > 0) {
            if (joinPoint.getArgs()[0] instanceof MultipartFile) {
                exceptionLog.setRequestParam("file");
            } else {
                exceptionLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
            }
        }
        if (Objects.nonNull(apiOperation)) {
            exceptionLog.setOptDesc(apiOperation.value());
        } else {
            exceptionLog.setOptDesc("");
        }
        exceptionLog.setExceptionInfo(ExceptionUtil.getTrace(e));
        String ipAddress = IpUtil.getIpAddress(request);
        exceptionLog.setIpAddress(ipAddress);
        exceptionLog.setIpSource(IpUtil.getIpSource(ipAddress));
        applicationContext.publishEvent(new ExceptionLogEvent(exceptionLog));
    }

}

package com.wusuowei.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)                 //定义我们的注解可以修饰谁
@Retention(RetentionPolicy.RUNTIME)        //定义我们的注解何时有效
@Documented
public @interface OptLog {

    String optType() default "";
}

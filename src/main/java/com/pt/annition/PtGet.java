package com.pt.annition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author :pt
 * @Date :Created in 14:46 2020/1/6
 */
//表示该标签的作用范围
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PtGet {
     String value()default "";
     String procude() default "";
     String comsumer() default "";
}

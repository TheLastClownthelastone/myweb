package com.pt.annition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author :pt
 * @Date :Created in 16:30 2020/1/6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PtPost {
    String value()default "";
    String procude() default "";
    String comsumer() default "";
}

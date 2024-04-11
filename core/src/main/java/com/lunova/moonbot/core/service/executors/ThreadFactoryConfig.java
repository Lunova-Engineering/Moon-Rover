package com.lunova.moonbot.core.service.executors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ThreadFactoryConfig {
    String nameFormat() default "";
    boolean daemon() default false;
    int priority() default Thread.NORM_PRIORITY;
    Class<? extends DefaultUncaughtExceptionHandler> exceptionHandler() default DefaultUncaughtExceptionHandler.class;
}

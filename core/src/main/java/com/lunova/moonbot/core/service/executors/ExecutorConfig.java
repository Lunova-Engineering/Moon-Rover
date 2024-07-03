package com.lunova.moonbot.core.service.executors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExecutorConfig {
    boolean scheduled() default false;

    int corePoolSize() default 0;

    int maximumPoolSize() default 1;

    long keepAliveTime() default 0;

    TimeUnit unit() default TimeUnit.NANOSECONDS;

    boolean daemon() default false;

    int priority() default Thread.NORM_PRIORITY;
}

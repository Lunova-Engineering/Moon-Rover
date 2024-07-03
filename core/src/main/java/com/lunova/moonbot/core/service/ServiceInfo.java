package com.lunova.moonbot.core.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceInfo {

    String name();

    boolean critical() default false;

    boolean disabled() default false;

    int priority() default 0;
}

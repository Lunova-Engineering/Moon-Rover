package com.lunova.moonbot.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to provide metadata for bot commands.
 * <p>
 * Used to annotate command classes with specific information such as the command's name.
 * This helps in registering, managing, and identifying commands based on their annotations.
 * </p>
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Bot
 * @since 10.12.2023
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAnnotation {

    /**
     * Specifies the unique name for the annotated bot command.
     *
     * @return the name of the bot command.
     */
    String name();

}

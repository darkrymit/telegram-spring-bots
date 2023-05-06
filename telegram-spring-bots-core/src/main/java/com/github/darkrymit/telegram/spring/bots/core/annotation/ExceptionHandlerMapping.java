package com.github.darkrymit.telegram.spring.bots.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * Indicate exception mapping information for a Telegram exception handler method.
 * <p>
 * If the annotation is used on a class marked {@link TelegramController}, it attributes will be
 * combined across all methods marked {@link ExceptionHandlerMapping} inside that class.
 * <p>
 * If the annotation is used on a method inside a class marked {@link TelegramController}, it will
 * mark that method as an exception handler method only for {@link HandlerMapping} methods inside
 * that class and override any class-level mapping information if information specified.
 * <p>
 * If the annotation is used on a class marked {@link ControllerAdvice}, it attributes will be
 * combined across all methods marked {@link ExceptionHandlerMapping} inside that class.
 * <p>
 * If the annotation is used on a method inside a class marked {@link ControllerAdvice}, it will
 * mark that method as a global exception handler method and override any class-level mapping
 * information if information specified.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionHandlerMapping {

  /**
   * Alias for the {@link #priority()} attribute.
   */
  @AliasFor("priority") int value() default 0;

  /**
   * The priority of this mapping. The priority determines the order in which the handler methods
   * are invoked. The higher the value, the higher the priority. The default priority is 0.
   */
  @AliasFor("value") int priority() default 0;

}

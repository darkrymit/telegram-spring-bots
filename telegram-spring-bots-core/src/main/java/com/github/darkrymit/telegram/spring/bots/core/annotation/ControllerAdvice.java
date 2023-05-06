package com.github.darkrymit.telegram.spring.bots.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * Indicates that an annotated class is advice to all "Telegram controllers".
 *
 * <p>This annotation serves as a specialization of the @Component annotation with specific
 * behavior for registering exception handling for "Telegram controllers" globally.
 *
 * <p>Classes annotated with {@link  ControllerAdvice} will be automatically detected by Spring and
 * registered as a bean within the application context.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface ControllerAdvice {

}
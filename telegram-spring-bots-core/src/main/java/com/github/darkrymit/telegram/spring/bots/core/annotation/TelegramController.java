package com.github.darkrymit.telegram.spring.bots.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * Indicates that an annotated class is a "Telegram controller". This annotation serves as a
 * specialization of the @Component annotation with specific behavior for handling Telegram
 * requests. Classes annotated with {@link  TelegramController} will be automatically detected by
 * Spring and registered as a bean within the application context.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface TelegramController {

  /**
   * The value may indicate a suggestion for a logical component name, to be turned into a Spring
   * bean in case of an autodetected component.
   *
   * @return the suggested component name, if any (or empty String otherwise)
   */
  @AliasFor(annotation = Component.class, attribute = "value")
  String value() default "";

}
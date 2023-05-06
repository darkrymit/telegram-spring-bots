package com.github.darkrymit.telegram.spring.bots.spel;

import com.github.darkrymit.telegram.spring.bots.core.annotation.TelegramController;
import com.github.darkrymit.telegram.spring.bots.core.annotation.HandlerMapping;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.intellij.lang.annotations.Language;
import org.springframework.core.annotation.AliasFor;

/**
 * The SpEL expression that evaluated at runtime and should return a boolean value. If the value is
 * {@code true}, the handler is enabled; otherwise, it is disabled.
 *
 * <p>This annotation can be used on a class or a method.
 * When used on a class market {@link TelegramController}, it combined across all methods marked
 * {@link HandlerMapping}. When used on a method marked {@link HandlerMapping} inside class marked
 * {@link TelegramController}, it enables or disables only that handler.
 *
 * <p>The SpEL expression can be specified as the annotation value, or using the
 * {@link #condition()} attribute.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SpELContainer.class)
@Documented
public @interface SpEL {

  /**
   * Alias for the {@link #condition()} attribute.
   */
  @AliasFor("condition") @Language(value = "SpEL")
  String[] value() default {};

  /**
   * The SpEL expression to evaluate.
   */
  @AliasFor("value") @Language(value = "SpEL")
  String[] condition() default {};

  int priority() default 0;
}

package com.github.darkrymit.telegram.spring.bots.core.annotation;

import com.github.darkrymit.telegram.spring.bots.core.UpdateType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsUpdateType {

  /**
   * Alias for the {@link #updateTypes()} attribute.
   */
  @AliasFor("updateTypes")
  UpdateType[] value() default {};

  /**
   * The update types supported by this handler.
   *
   * <p>If the update type is not specified, the handler will never be executed.
   */
  @AliasFor("value")
  UpdateType[] updateTypes() default {};

  int priority() default 1000;
}

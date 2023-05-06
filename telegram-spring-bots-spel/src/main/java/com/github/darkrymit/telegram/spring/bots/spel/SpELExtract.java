package com.github.darkrymit.telegram.spring.bots.spel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.intellij.lang.annotations.Language;
import org.springframework.core.annotation.AliasFor;

@Target({ElementType.PARAMETER,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpELExtract {
  @AliasFor("condition") @Language(value = "SpEL")
  String value() default "";

  @AliasFor("value") @Language(value = "SpEL")
  String condition() default "";
}

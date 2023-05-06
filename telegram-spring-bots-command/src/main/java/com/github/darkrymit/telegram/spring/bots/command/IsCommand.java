package com.github.darkrymit.telegram.spring.bots.command;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsCommand {

  /**
   * Alias for the {@link #commands()} attribute.
   */
  @AliasFor("commands")
  String[] value() default {};

  /**
   * The commands supported by this handler.
   *
   * <p>If the command is not specified, the handler will be executed for all commands.
   */
  @AliasFor("value")
  String[] commands() default {};

  int priority() default 1000;

  boolean requireMentionInGroup() default true;
}

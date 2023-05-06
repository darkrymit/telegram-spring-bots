package com.github.darkrymit.telegram.spring.bots.core.bind.annotation;

import com.github.darkrymit.telegram.spring.bots.core.bind.ChatMemberUpdatedType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChatMemberUpdatedTypes {

  ChatMemberUpdatedType[] value() default {};
}

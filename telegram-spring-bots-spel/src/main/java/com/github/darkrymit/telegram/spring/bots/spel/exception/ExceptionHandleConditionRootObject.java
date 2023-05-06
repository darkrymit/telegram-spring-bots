package com.github.darkrymit.telegram.spring.bots.spel.exception;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.spel.TelegramRequestRootObject;
import org.springframework.lang.Nullable;

public class ExceptionHandleConditionRootObject extends TelegramRequestRootObject {

  @Nullable
  public final Object handler;

  public final Exception exception;

  public ExceptionHandleConditionRootObject(TelegramRequest event,@Nullable Object handler,
      Exception exception) {
    super(event);
    this.handler = handler;
    this.exception = exception;
  }
}

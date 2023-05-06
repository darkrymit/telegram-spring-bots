package com.github.darkrymit.telegram.spring.bots.core.exception.handler;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.support.ModelAndView;
import org.springframework.lang.Nullable;


public interface ExceptionHandlerAdapter {

  boolean supports(Object exceptionHandler);

  @Nullable
  ModelAndView handleException(TelegramRequest event,@Nullable Object handler,Exception exception,Object exceptionHandler) throws Exception;
}

package com.github.darkrymit.telegram.spring.bots.core.handler.interceptor;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;


public class LoggingInterceptor implements HandlerInterceptor {

  Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

  @Override
  public boolean preHandle(TelegramRequest event, Object handler) throws Exception {
    log.debug("Called preHandle for event {} with handler {}", event, handler);
    return true;
  }

  @Override
  public void postHandle(TelegramRequest event, Object handler) throws Exception {
    log.debug("Called postHandle for event {} with handler {}", event, handler);
  }

  @Override
  public void afterCompletion(TelegramRequest event, Object handler,@Nullable Exception exception)
      throws Exception {
    log.debug("Called afterCompletion for event {} with handler {} and exception {}", event,
        handler, exception == null ? "null" : exception.getMessage());
  }
}

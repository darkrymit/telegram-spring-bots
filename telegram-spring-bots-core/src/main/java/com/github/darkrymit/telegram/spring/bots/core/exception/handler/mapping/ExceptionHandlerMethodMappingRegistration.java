package com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping;

import com.github.darkrymit.telegram.spring.bots.core.method.HandlerMethod;

public class ExceptionHandlerMethodMappingRegistration implements
    ExceptionHandlerMappingRegistration {

  private final HandlerMethod handlerMethod;

  public ExceptionHandlerMethodMappingRegistration(HandlerMethod handlerMethod) {
    this.handlerMethod = handlerMethod;
  }

  @Override
  public HandlerMethod getExceptionHandler() {
    return handlerMethod;
  }
}

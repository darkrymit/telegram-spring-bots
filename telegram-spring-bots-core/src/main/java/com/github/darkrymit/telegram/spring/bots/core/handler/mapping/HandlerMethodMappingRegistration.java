package com.github.darkrymit.telegram.spring.bots.core.handler.mapping;

import com.github.darkrymit.telegram.spring.bots.core.method.HandlerMethod;

public class HandlerMethodMappingRegistration implements HandlerMappingRegistration {

  private final HandlerMethod handlerMethod;

  public HandlerMethodMappingRegistration(HandlerMethod handlerMethod) {
    this.handlerMethod = handlerMethod;
  }

  @Override
  public HandlerMethod getHandler() {
    return handlerMethod;
  }
}

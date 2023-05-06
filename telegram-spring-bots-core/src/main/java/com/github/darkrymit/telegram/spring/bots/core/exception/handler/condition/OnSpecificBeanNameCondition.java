package com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.method.HandlerMethod;

public class OnSpecificBeanNameCondition implements ExceptionHandleCondition {
  private final String beanName;
  private final int priority;

  public OnSpecificBeanNameCondition(String beanName) {
    this(beanName, Integer.MAX_VALUE);
  }

  public OnSpecificBeanNameCondition(String beanName, int priority) {
    this.beanName = beanName;
    this.priority = priority;
  }

  @Override
  public boolean isTrue(TelegramRequest request, Object handler, Exception exception) {
    if (!(handler instanceof HandlerMethod)) {
      return false;
    }
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    return handlerMethod.getBeanName().equals(beanName);
  }

  @Override
  public int getPriority() {
    return priority;
  }
}

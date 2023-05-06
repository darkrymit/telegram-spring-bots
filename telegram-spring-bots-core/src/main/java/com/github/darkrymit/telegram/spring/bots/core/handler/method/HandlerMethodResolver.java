package com.github.darkrymit.telegram.spring.bots.core.handler.method;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.handler.HandlerExecutionChain;
import com.github.darkrymit.telegram.spring.bots.core.handler.HandlerResolver;
import com.github.darkrymit.telegram.spring.bots.core.handler.interceptor.HandlerInterceptor;
import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.HandlerMappingInfo;
import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.HandlerMappingRegistry;
import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.HandlerMethodMappingRegistration;
import com.github.darkrymit.telegram.spring.bots.core.method.HandlerMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

public class HandlerMethodResolver implements HandlerResolver {

  private static final Logger log = LoggerFactory.getLogger(HandlerMethodResolver.class);

  private final HandlerMappingRegistry registry;

  private final List<HandlerInterceptor> interceptors;

  public HandlerMethodResolver(HandlerMappingRegistry registry,
      List<HandlerInterceptor> interceptors) {
    this.registry = registry;
    this.interceptors = new ArrayList<>();
    this.interceptors.addAll(interceptors);
  }

  public void addInterceptor(HandlerInterceptor interceptor) {
    this.interceptors.add(interceptor);
  }

  public void addInterceptors(List<HandlerInterceptor> interceptors) {
    this.interceptors.addAll(interceptors);
  }

  @Override
  public HandlerExecutionChain getHandler(TelegramRequest event) {
    Object handler = lookupHandlerMethod(event);
    if (handler == null) {
      return null;
    }

    HandlerExecutionChain executionChain = getHandlerExecutionChain(handler);

    log.debug("Mapped to {}",executionChain);

    return executionChain;
  }

  @Nullable
  protected HandlerMethod lookupHandlerMethod(TelegramRequest event) {
    for (HandlerMappingInfo handlerMappingInfo : registry.getSortedMappings()) {
      if (handlerMappingInfo.getCondition().isTrue(event)) {
        return ((HandlerMethodMappingRegistration) Objects.requireNonNull(registry.getRegistration(
            handlerMappingInfo))).getHandler();
      }
    }
    return null;
  }

  protected HandlerExecutionChain getHandlerExecutionChain(Object handler) {
    HandlerExecutionChain chain = new HandlerExecutionChain(handler);
    chain.addInterceptors(interceptors);
    return chain;
  }
}

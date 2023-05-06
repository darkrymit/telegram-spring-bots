package com.github.darkrymit.telegram.spring.bots.core.method.response;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;

/**
 * Resolves method parameters by delegating to a list of registered
 * {@link HandlerMethodReturnValueHandler} handlers.
 */
@Slf4j
public class HandlerMethodReturnValueHandlerComposite implements HandlerMethodReturnValueHandler {

  private final List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();

  /**
   * Create a composite handler for the given {@link HandlerMethodReturnValueHandler}s.
   *
   * @param handlers handlers to add
   */
  public HandlerMethodReturnValueHandlerComposite(
      List<? extends HandlerMethodReturnValueHandler> handlers) {
    returnValueHandlers.addAll(handlers);
  }

  public HandlerMethodReturnValueHandlerComposite addHandler(
      HandlerMethodReturnValueHandler handler) {
    returnValueHandlers.add(handler);
    return this;
  }

  public HandlerMethodReturnValueHandlerComposite addHandlers(
      List<? extends HandlerMethodReturnValueHandler> handlers) {
    returnValueHandlers.addAll(handlers);
    return this;
  }

  public List<HandlerMethodReturnValueHandler> getReturnValueHandlers() {
    return Collections.unmodifiableList(returnValueHandlers);
  }

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    return getReturnValueHandler(returnType) != null;
  }

  private HandlerMethodReturnValueHandler getReturnValueHandler(MethodParameter returnType) {
    for (HandlerMethodReturnValueHandler handler : this.returnValueHandlers) {
      if (handler.supportsReturnType(returnType)) {
        return handler;
      }
    }
    return null;
  }

  /**
   * Iterate over registered {@link HandlerMethodReturnValueHandler}s and invoke first one that
   * supports it.
   */
  @Override
  public void handleReturnValue(Object returnValue, MethodParameter returnType, TelegramRequest request,
      ModelAndViewContainer container)
      throws Exception {
    HandlerMethodReturnValueHandler handler = getReturnValueHandler(returnType);
    if (handler == null) {
      throw new IllegalStateException(
          "Unknown return value type: " + returnType.getParameterType().getName());
    }
    handler.handleReturnValue(returnValue, returnType, request,container);
  }
}

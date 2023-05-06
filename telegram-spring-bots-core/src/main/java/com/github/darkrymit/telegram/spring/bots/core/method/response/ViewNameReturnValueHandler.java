package com.github.darkrymit.telegram.spring.bots.core.method.response;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import org.springframework.core.MethodParameter;

public class ViewNameReturnValueHandler implements HandlerMethodReturnValueHandler {

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    return String.class.equals(returnType.getParameterType());
  }

  @Override
  public void handleReturnValue(Object returnValue, MethodParameter returnType, TelegramRequest request,
      ModelAndViewContainer container) {
    container.setViewName((String) returnValue);
  }
}

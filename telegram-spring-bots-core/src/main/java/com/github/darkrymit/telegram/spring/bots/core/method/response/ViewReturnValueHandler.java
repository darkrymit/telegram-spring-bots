package com.github.darkrymit.telegram.spring.bots.core.method.response;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.github.darkrymit.telegram.spring.bots.core.support.View;
import org.springframework.core.MethodParameter;

public class ViewReturnValueHandler implements HandlerMethodReturnValueHandler {

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    return View.class.isAssignableFrom(returnType.getParameterType());
  }

  @Override
  public void handleReturnValue(Object returnValue, MethodParameter returnType, TelegramRequest request,
      ModelAndViewContainer container) {
    container.setView((View) returnValue);
  }
}

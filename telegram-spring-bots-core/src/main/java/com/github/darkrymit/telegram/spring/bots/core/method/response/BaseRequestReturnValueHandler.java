package com.github.darkrymit.telegram.spring.bots.core.method.response;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.pengrad.telegrambot.request.BaseRequest;
import org.springframework.core.MethodParameter;

public class BaseRequestReturnValueHandler implements HandlerMethodReturnValueHandler {

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    return BaseRequest.class.isAssignableFrom(returnType.getParameterType());
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public void handleReturnValue(Object returnValue, MethodParameter returnType, TelegramRequest request,
      ModelAndViewContainer container)
      throws Exception {
    container.setRequestHandled(true);
    request.getBot().execute((BaseRequest) returnValue);
  }
}

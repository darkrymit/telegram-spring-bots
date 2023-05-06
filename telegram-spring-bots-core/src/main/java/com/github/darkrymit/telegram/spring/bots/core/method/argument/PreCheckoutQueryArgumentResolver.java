package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.pengrad.telegrambot.model.PreCheckoutQuery;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;

public class PreCheckoutQueryArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return PreCheckoutQuery.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  @Nullable
  public PreCheckoutQuery resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {
    return request.getUpdate().preCheckoutQuery();
  }
}

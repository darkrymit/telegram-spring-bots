package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.BotRegistration;
import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.bind.annotation.RequestAttributes;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import java.util.Map;
import org.springframework.core.MethodParameter;

public class TelegramRequestArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    Class<?> type = parameter.getParameterType();
    return TelegramRequest.class.isAssignableFrom(type) || TelegramBot.class.isAssignableFrom(type)
        || BotRegistration.class.isAssignableFrom(type) || Update.class.isAssignableFrom(type) || (
        Map.class.isAssignableFrom(type) && parameter.hasParameterAnnotation(
            RequestAttributes.class));
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {

    Class<?> type = parameter.getParameterType();
    if (TelegramRequest.class.isAssignableFrom(type)) {
      return request;
    } else if (TelegramBot.class.isAssignableFrom(type)) {
      return request.getBot();
    } else if (BotRegistration.class.isAssignableFrom(type)) {
      return request.getBotRegistration();
    } else if (Update.class.isAssignableFrom(type)) {
      return request.getUpdate();
    }
    return request.getAttributes();
  }
}

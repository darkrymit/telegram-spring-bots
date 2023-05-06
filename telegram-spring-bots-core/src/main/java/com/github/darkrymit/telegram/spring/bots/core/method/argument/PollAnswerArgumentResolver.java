package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.pengrad.telegrambot.model.PollAnswer;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;

public class PollAnswerArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return PollAnswer.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  @Nullable
  public PollAnswer resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {
    return request.getUpdate().pollAnswer();
  }
}

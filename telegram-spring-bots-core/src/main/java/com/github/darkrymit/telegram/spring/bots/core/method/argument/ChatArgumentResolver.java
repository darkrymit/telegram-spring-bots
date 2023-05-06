package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.github.darkrymit.telegram.spring.bots.core.session.chat.ChatSession;
import com.github.darkrymit.telegram.spring.bots.core.session.chat.ChatSessionResolver;
import com.pengrad.telegrambot.model.Chat;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;

public class ChatArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Chat.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  @Nullable
  public Chat resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {
    return request.getChat();
  }
}

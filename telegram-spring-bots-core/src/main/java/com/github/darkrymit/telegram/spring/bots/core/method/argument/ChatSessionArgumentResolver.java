package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.github.darkrymit.telegram.spring.bots.core.session.chat.ChatSession;
import com.github.darkrymit.telegram.spring.bots.core.session.chat.ChatSessionResolver;
import com.pengrad.telegrambot.model.Chat;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;

public class ChatSessionArgumentResolver implements HandlerMethodArgumentResolver {

  private final ChatSessionResolver chatSessionResolver;

  public ChatSessionArgumentResolver(ChatSessionResolver chatSessionResolver) {
    this.chatSessionResolver = chatSessionResolver;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return ChatSession.class.isAssignableFrom(parameter.getParameterType())
        || ChatSessionResolver.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  @Nullable
  public Object resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {
    Chat chat = request.getChat();
    if (chat == null) {
      return null;
    }
    if (ChatSessionResolver.class.isAssignableFrom(parameter.getParameterType())) {
      return chatSessionResolver;
    }
    return chatSessionResolver.getOrCreate(chat.id());
  }
}

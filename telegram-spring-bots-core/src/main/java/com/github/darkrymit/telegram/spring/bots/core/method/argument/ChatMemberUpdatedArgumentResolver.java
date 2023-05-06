package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.ChatMemberUpdatedType;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.bind.annotation.ChatMemberUpdatedTypes;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.pengrad.telegrambot.model.ChatMemberUpdated;
import com.pengrad.telegrambot.model.Update;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

public class ChatMemberUpdatedArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return ChatMemberUpdated.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  @Nullable
  public ChatMemberUpdated resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {

    Update update = request.getUpdate();

    ChatMemberUpdatedTypes annotation = AnnotatedElementUtils.findMergedAnnotation(parameter.getParameter(),
        ChatMemberUpdatedTypes.class);

    List<ChatMemberUpdatedType> types = annotation != null ? List.of(annotation.value()) : List.of();

    if (update.myChatMember() != null && types.contains(ChatMemberUpdatedType.MY_CHAT_MEMBER)) {
      return update.myChatMember();
    }

    return update.chatMember();
  }
}

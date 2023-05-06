package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.MessageType;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.bind.annotation.MessageTypes;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

public class MessageArgumentResolver implements HandlerMethodArgumentResolver {

  private static boolean targetTypeOrEmpty(MessageType targetType, List<MessageType> types) {
    return types.isEmpty() || types.contains(targetType);
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Message.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  @Nullable
  public Message resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {

    Update update = request.getUpdate();

    MessageTypes annotation = AnnotatedElementUtils.findMergedAnnotation(parameter.getParameter(),
        MessageTypes.class);

    List<MessageType> types = annotation != null ? List.of(annotation.value()) : List.of();

    if (update.message() != null && targetTypeOrEmpty(MessageType.MESSAGE, types)) {
      return update.message();
    }

    if (update.editedMessage() != null && targetTypeOrEmpty(MessageType.EDITED_MESSAGE, types)) {
      return update.editedMessage();
    }

    if (update.channelPost() != null && targetTypeOrEmpty(MessageType.CHANNEL_POST, types)) {
      return update.channelPost();
    }

    if (update.editedChannelPost() != null && targetTypeOrEmpty(MessageType.EDITED_CHANNEL_POST,
        types)) {
      return update.editedChannelPost();
    }

    return null;
  }
}

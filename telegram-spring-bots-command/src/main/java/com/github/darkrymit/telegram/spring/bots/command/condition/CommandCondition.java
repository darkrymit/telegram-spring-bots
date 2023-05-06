package com.github.darkrymit.telegram.spring.bots.command.condition;

import com.github.darkrymit.telegram.spring.bots.command.support.CommandUtils;
import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleCondition;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleCondition;
import com.pengrad.telegrambot.model.Chat.Type;
import com.pengrad.telegrambot.model.Message;
import java.util.Set;
import org.springframework.lang.Nullable;

public class CommandCondition implements HandleCondition, ExceptionHandleCondition {

  private final Set<String> commands;

  private final boolean requireMentionInNotPrivate;

  private final int priority;

  public CommandCondition(Set<String> commands, boolean requireMentionInGroup, int priority) {
    this.commands = commands;
    this.requireMentionInNotPrivate = requireMentionInGroup;
    this.priority = priority;
  }

  private static @Nullable Message getMessage(TelegramRequest request) {
    return request.getUpdate().message();
  }

  @Override
  public boolean isTrue(TelegramRequest request) {
    return isNeededCommand(request);
  }

  @Override
  public boolean isTrue(TelegramRequest request, Object handler, Exception exception) {
    return isNeededCommand(request);
  }

  @Override
  public int getPriority() {
    return priority;
  }

  private boolean isNeededCommand(TelegramRequest request) {
    Message message = getMessage(request);
    if (message == null) {
      return false;
    }
    String command = CommandUtils.extractCommand(message);
    if (command.isBlank()) {
      return false;
    }
    String commandName = CommandUtils.extractCommandName(command);
    String mention = CommandUtils.extractMention(command);
    if (message.chat().type() != Type.Private && requireMentionInNotPrivate && (!mention.equals(
        request.getBotMetaInfo().getNickName()))) {
      return false;
    }
    if (commands.isEmpty()) {
      return true;
    }
    return commands.contains(commandName);
  }


}

package com.github.darkrymit.telegram.spring.bots.command.support;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.MessageEntity;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;

public class CommandUtils {

  private CommandUtils() {
  }

  public static Set<String> filterCommands(String[] commands) {
    return Arrays.stream(commands).map(String::strip).filter(s -> !s.isBlank())
        .collect(Collectors.toSet());
  }

  public static String extractCommandName(String command) {
    int i = command.indexOf('@');
    if (i == -1) {
      return command;
    }
    return command.substring(0, i);
  }

  public static String extractCommandName(Message message) {
    String command = extractCommand(message);
    return extractCommandName(command);
  }

  public static String extractMention(String command) {
    int i = command.indexOf('@');
    if (i == -1) {
      return "";
    }
    return command.substring(i + 1);
  }

  public static String extractMention(Message message) {
    String command = extractCommand(message);
    return extractMention(command);
  }

  public static String extractCommand(Message message) {
    MessageEntity messageEntity = getCommandEntity(message);
    if (messageEntity == null) {
      return "";
    }
    if (!isContainsOnlyWhitespaces(message.text(), 0, messageEntity.offset())) {
      return "";
    }
    return message.text()
        .substring(messageEntity.offset() + 1, messageEntity.offset() + messageEntity.length());
  }

  public static String extractCommandBody(Message message) {
    MessageEntity messageEntity = getCommandEntity(message);
    if (messageEntity == null) {
      return "";
    }
    if (!isContainsOnlyWhitespaces(message.text(), 0, messageEntity.offset())) {
      return "";
    }
    return message.text().substring(messageEntity.offset() + messageEntity.length());
  }

  public static boolean isContainsOnlyWhitespaces(String text, int offset, int length) {
    for (int i = offset; i < offset + length; i++) {
      if (!Character.isWhitespace(text.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  @Nullable
  public static MessageEntity getCommandEntity(Message message) {
    if (message.entities() == null || message.entities().length == 0) {
      return null;
    }
    MessageEntity messageEntity = message.entities()[0];
    if (messageEntity.type() != MessageEntity.Type.bot_command) {
      return null;
    }
    return messageEntity;
  }
}

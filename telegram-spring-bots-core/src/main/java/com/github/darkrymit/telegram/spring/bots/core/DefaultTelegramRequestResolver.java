package com.github.darkrymit.telegram.spring.bots.core;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
public class DefaultTelegramRequestResolver implements TelegramRequestResolver {


  @Override
  public TelegramRequest resolve(BotRegistration botRegistration, Update update) {
    UpdateType updateType = getUpdateType(update);
    Chat chat = getChat(update, updateType);
    User user = getUser(update, updateType);
    return new TelegramRequest(botRegistration, update, updateType, chat, user);
  }

  protected @Nullable User getUser(Update update, UpdateType updateType) {
    if (updateType == UpdateType.MESSAGE) {
      return update.message().from();
    }
    if (updateType == UpdateType.EDITED_MESSAGE) {
      return update.editedMessage().from();
    }
    if (updateType == UpdateType.CHANNEL_POST) {
      return update.channelPost().from();
    }
    if (updateType == UpdateType.EDITED_CHANNEL_POST) {
      return update.editedChannelPost().from();
    }
    if (updateType == UpdateType.INLINE_QUERY) {
      return update.inlineQuery().from();
    }
    if (updateType == UpdateType.CHOSEN_INLINE_RESULT) {
      return update.chosenInlineResult().from();
    }
    if (updateType == UpdateType.CALLBACK_QUERY) {
      return update.callbackQuery().from();
    }
    if (updateType == UpdateType.SHIPPING_QUERY) {
      return update.shippingQuery().from();
    }
    if (updateType == UpdateType.PRE_CHECKOUT_QUERY) {
      return update.preCheckoutQuery().from();
    }
    if (updateType == UpdateType.POLL_ANSWER) {
      return update.pollAnswer().user();
    }
    if (updateType == UpdateType.MY_CHAT_MEMBER) {
      return update.myChatMember().from();
    }
    if (updateType == UpdateType.CHAT_MEMBER) {
      return update.chatMember().from();
    }
    if (updateType == UpdateType.CHAT_JOIN_REQUEST) {
      return update.chatJoinRequest().from();
    }
    return null;
  }

  protected @Nullable Chat getChat(Update update, UpdateType updateType) {
    if (updateType == UpdateType.MESSAGE) {
      return update.message().chat();
    }
    if (updateType == UpdateType.EDITED_MESSAGE) {
      return update.editedMessage().chat();
    }
    if (updateType == UpdateType.CHANNEL_POST) {
      return update.channelPost().chat();
    }
    if (updateType == UpdateType.EDITED_CHANNEL_POST) {
      return update.editedChannelPost().chat();
    }
    if (updateType == UpdateType.MY_CHAT_MEMBER) {
      return update.myChatMember().chat();
    }
    if (updateType == UpdateType.CALLBACK_QUERY && update.callbackQuery().message() != null) {
      return update.callbackQuery().message().chat();
    }
    if (updateType == UpdateType.CHAT_MEMBER) {
      return update.chatMember().chat();
    }
    if (updateType == UpdateType.CHAT_JOIN_REQUEST) {
      return update.chatJoinRequest().chat();
    }
    return null;
  }

  @NotNull
  private UpdateType getUpdateType(Update update) {
    return UpdateType.from(update);
  }

}

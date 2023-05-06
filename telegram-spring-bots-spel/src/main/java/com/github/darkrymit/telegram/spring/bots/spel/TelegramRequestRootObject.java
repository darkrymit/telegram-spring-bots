package com.github.darkrymit.telegram.spring.bots.spel;

import com.github.darkrymit.telegram.spring.bots.core.BotMetaInfo;
import com.github.darkrymit.telegram.spring.bots.core.BotRegistration;
import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.UpdateType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.ChatJoinRequest;
import com.pengrad.telegrambot.model.ChatMemberUpdated;
import com.pengrad.telegrambot.model.ChosenInlineResult;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Poll;
import com.pengrad.telegrambot.model.PollAnswer;
import com.pengrad.telegrambot.model.PreCheckoutQuery;
import com.pengrad.telegrambot.model.ShippingQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import java.util.Map;
import org.springframework.lang.Nullable;

public class TelegramRequestRootObject {

  public final TelegramRequest request;

  public final BotRegistration botRegistration;

  public final BotMetaInfo metaInfo;
  public final String botName;
  public final TelegramBot bot;
  public final Update update;
  public final UpdateType updateType;
  @Nullable
  public final Chat chat;
  @Nullable
  public final User user;
  public final Map<String,Object> attributes;
  public final Message message;
  public final Message editedMessage;
  public final Message channelPost;
  public final Message editedChannelPost;
  public final InlineQuery inlineQuery;
  public final ChosenInlineResult chosenInlineResult;
  public final CallbackQuery callbackQuery;
  public final ShippingQuery shippingQuery;
  public final PreCheckoutQuery preCheckoutQuery;
  public final Poll poll;
  public final PollAnswer pollAnswer;
  public final ChatMemberUpdated myChatMember;
  public final ChatMemberUpdated chatMember;
  public final ChatJoinRequest chatJoinRequest;

  public TelegramRequestRootObject(TelegramRequest request) {
    this.request = request;
    this.botRegistration = request.getBotRegistration();
    this.metaInfo = request.getBotMetaInfo();
    this.botName = request.getBotName();
    this.bot = request.getBot();
    this.update = request.getUpdate();
    this.updateType = request.getUpdateType();
    this.chat = request.getChat();
    this.user = request.getUser();
    this.attributes = request.getAttributes();
    this.message = update.message();
    this.editedMessage = update.editedMessage();
    this.channelPost = update.channelPost();
    this.editedChannelPost = update.editedChannelPost();
    this.inlineQuery = update.inlineQuery();
    this.chosenInlineResult = update.chosenInlineResult();
    this.callbackQuery = update.callbackQuery();
    this.shippingQuery = update.shippingQuery();
    this.preCheckoutQuery = update.preCheckoutQuery();
    this.poll = update.poll();
    this.pollAnswer = update.pollAnswer();
    this.myChatMember = update.myChatMember();
    this.chatMember = update.chatMember();
    this.chatJoinRequest = update.chatJoinRequest();
  }
}

package com.github.darkrymit.telegram.spring.bots.core;

import com.pengrad.telegrambot.model.Update;

/**
 * Represents the type of {@link Update} that is being processed.
 *
 * @see Update
 */
public enum UpdateType {
  /**
   * Used when {@link Update#message()} is not null for the current telegram request.
   */
  MESSAGE,

  /**
   * Used when {@link Update#editedMessage()} is not null for the current telegram request.
   */
  EDITED_MESSAGE,

  /**
   * Used when {@link Update#channelPost()} is not null for the current telegram request.
   */
  CHANNEL_POST,

  /**
   * Used when {@link Update#editedChannelPost()} is not null for the current telegram request.
   */
  EDITED_CHANNEL_POST,

  /**
   * Used when {@link Update#inlineQuery()} is not null for the current telegram request.
   */
  INLINE_QUERY,

  /**
   * Used when {@link Update#chosenInlineResult()} is not null for the current telegram request.
   */
  CHOSEN_INLINE_RESULT,

  /**
   * Used when {@link Update#callbackQuery()} is not null for the current telegram request.
   */
  CALLBACK_QUERY,

  /**
   * Used when {@link Update#shippingQuery()} is not null for the current telegram request.
   */
  SHIPPING_QUERY,

  /**
   * Used when {@link Update#preCheckoutQuery()} is not null for the current telegram request.
   */
  PRE_CHECKOUT_QUERY,

  /**
   * Used when {@link Update#poll()} is not null for the current telegram request.
   */
  POLL,

  /**
   * Used when {@link Update#pollAnswer()} is not null for the current telegram request.
   */
  POLL_ANSWER,

  /**
   * Used when {@link Update#myChatMember()} is not null for the current telegram request.
   */
  MY_CHAT_MEMBER,

  /**
   * Used when {@link Update#chatMember()} is not null for the current telegram request.
   */
  CHAT_MEMBER,

  /**
   * Used when {@link Update#chatJoinRequest()} is not null for the current telegram request.
   */
  CHAT_JOIN_REQUEST,

  /**
   * Used for a new update type that is not yet supported by this library.
   */
  UNSUPPORTED;

  public static UpdateType from(Update update) {
    if (update.message() != null) {
      return MESSAGE;
    }
    if (update.editedMessage() != null) {
      return EDITED_MESSAGE;
    }
    if (update.channelPost() != null) {
      return CHANNEL_POST;
    }
    if (update.editedChannelPost() != null) {
      return EDITED_CHANNEL_POST;
    }
    if (update.inlineQuery() != null) {
      return INLINE_QUERY;
    }
    if (update.chosenInlineResult() != null) {
      return CHOSEN_INLINE_RESULT;
    }
    if (update.callbackQuery() != null) {
      return CALLBACK_QUERY;
    }
    if (update.shippingQuery() != null) {
      return SHIPPING_QUERY;
    }
    if (update.preCheckoutQuery() != null) {
      return PRE_CHECKOUT_QUERY;
    }
    if (update.poll() != null) {
      return POLL;
    }
    if (update.pollAnswer() != null) {
      return POLL_ANSWER;
    }
    if (update.myChatMember() != null) {
      return MY_CHAT_MEMBER;
    }
    if (update.chatMember() != null) {
      return CHAT_MEMBER;
    }
    if (update.chatJoinRequest() != null) {
      return CHAT_JOIN_REQUEST;
    }
    return UNSUPPORTED;
  }
}

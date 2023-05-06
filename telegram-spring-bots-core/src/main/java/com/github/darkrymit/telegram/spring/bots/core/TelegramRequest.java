package com.github.darkrymit.telegram.spring.bots.core;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.lang.Nullable;

/**
 * Represents an event that encapsulates an incoming update from the Telegram Bot API, along with
 * metadata about the update, such as the bot that received the update, the user and chat associated
 * with the update, and any session information.
 *
 * @see TelegramBot
 * @see Update
 */
public class TelegramRequest {

  /**
   * The Telegram bot registration that received the update.
   */
  private final BotRegistration botRegistration;

  /**
   * The incoming update from the Telegram Bot API.
   */
  private final Update update;

  /**
   * The type of the incoming update from the Telegram Bot API.
   */
  private final UpdateType updateType;

  /**
   * The chat resolved from the update, if any.
   */
  @Nullable
  private final Chat chat;

  /**
   * The user resolved from the update, if any.
   */
  @Nullable
  private final User user;

  /**
   * The attributes associated with the update, if any.
   */
  private final Map<String, Object> attributes = new HashMap<>();

  /**
   * Creates a new UpdateEvent with the specified parameters.
   *
   * @param botRegistration the TelegramBot and its meta info that received the update
   * @param update          the incoming update from the Telegram Bot API
   * @param updateType     the type of the incoming update from the Telegram Bot API
   * @param chat           the chat resolved from the update, if any
   * @param user          the user resolved from the update, if any
   * @see TelegramBot
   * @see Update
   */
  public TelegramRequest(BotRegistration botRegistration, Update update, UpdateType updateType,
      @Nullable Chat chat, @Nullable User user) {
    this.botRegistration = botRegistration;
    this.update = update;
    this.updateType = updateType;
    this.chat = chat;
    this.user = user;
  }

  /**
   * Returns the internal name of the TelegramBot that received the update.
   *
   * @return the TelegramBot instance
   * @see TelegramBot
   */
  public String getBotName() {
    return botRegistration.getMetaInfo().getInternalName();
  }


  /**
   * Returns the meta info of the TelegramBot that received the update.
   *
   * @return the TelegramBot instance
   * @see TelegramBot
   */
  public BotMetaInfo getBotMetaInfo() {
    return botRegistration.getMetaInfo();
  }

  /**
   * Returns the TelegramBot that received the update.
   *
   * @return the TelegramBot instance
   * @see TelegramBot
   */
  public TelegramBot getBot() {
    return botRegistration.getBot();
  }

  public BotRegistration getBotRegistration() {
    return botRegistration;
  }

  /**
   * Returns the incoming update from the Telegram Bot API.
   *
   * @return the Update instance
   * @see Update
   */
  public Update getUpdate() {
    return update;
  }

  /**
   * Returns the type of the incoming update from the Telegram Bot API.
   *
   * @return the UpdateType instance
   * @see UpdateType
   */
  public UpdateType getUpdateType() {
    return updateType;
  }

  /**
   * Returns the chat resolved from the update, if any.
   *
   * @return the Chat instance, or {@code null} if no chat is associated
   * @see Chat
   */
  @Nullable
  public Chat getChat() {
    return chat;
  }

  /**
   * Returns the user resolved from the update, if any.
   *
   * @return the User instance, or {@code null} if no user is associated
   * @see User
   */
  @Nullable
  public User getUser() {
    return user;
  }

  /**
   * Returns attribute by name associated with the update, if any.
   * @param name attribute name
   * @return the attribute value, or {@code null} if no attribute is associated
   */
  public Object getAttribute(String name) {
    return attributes.get(name);
  }

  /**
   * Returns attribute by name associated with the update, if any.
   * @param name attribute name
   * @param type attribute type
   * @return the attribute value wit cast to provided type, or {@code null} if no attribute is associated
   * @param <T> attribute type
   */
  public <T> T getAttribute(String name, Class<T> type) {
    return type.cast(attributes.get(name));
  }

  /**
   * Returns {@code true} if attribute by name associated with request. Otherwise, returns {@code false}.
   * @param name attribute name
   * @return {@code true} if attribute is associated with request. Otherwise, returns {@code false}.
   */
  public boolean hasAttribute(String name) {
    return attributes.containsKey(name);
  }

  /**
   * Sets attribute by name associated with the update.
   * @param name attribute name
   * @param value attribute value
   */
  public void setAttribute(String name, Object value) {
    attributes.put(name, value);
  }

  /**
   * Removes attribute by name associated with the update.
   * @param name attribute name
   */
  public void removeAttribute(String name) {
    attributes.remove(name);
  }

  /**
   * Returns map of attributes associated with the update, if any.
   *
   * @return the map of attributes
   */
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String toString() {
    return "TelegramRequest{" +
        "botRegistration=" + botRegistration +
        ", update=" + update +
        '}';
  }
}
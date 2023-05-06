package com.github.darkrymit.telegram.spring.bots.core.session.chat;

import com.github.darkrymit.telegram.spring.bots.core.session.user.UserSession;

/**
 * Interface for a component that resolves {@link ChatSession} objects.
 *
 * <p>Used to retrieve a {@link ChatSession} object, which can be used to
 * store and retrieve information about a user's interaction with the bot.
 */
public interface ChatSessionResolver {


  /**
   * Retrieves the {@link UserSession} for User or creates a new one if it doesn't exist.
   *
   * @param chatId the chat id
   * @return resolved value
   */
  ChatSession getOrCreate(Long chatId);

}


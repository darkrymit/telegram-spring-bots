package com.github.darkrymit.telegram.spring.bots.core.session.user;

/**
 * Interface for a component that resolves {@link UserSession} objects.
 *
 * <p>Used to retrieve a {@link UserSession} object, which can be used to
 * store and retrieve information about a user's interaction with the bot.
 */
public interface UserSessionResolver {

  /**
   * Retrieves the {@link UserSession} for User or creates a new one if it doesn't exist.
   *
   * @param userId the user id
   * @return resolved value
   */
  UserSession getOrCreate(Long userId);
}


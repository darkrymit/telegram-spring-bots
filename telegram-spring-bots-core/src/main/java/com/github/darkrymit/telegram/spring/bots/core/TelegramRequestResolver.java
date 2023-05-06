package com.github.darkrymit.telegram.spring.bots.core;

import com.pengrad.telegrambot.model.Update;

/**
 * This interface is used to resolve provided by
 * {@link com.github.darkrymit.telegram.spring.bots.core.Dispatcher} arguments to
 * {@link TelegramRequest}.
 *
 * @see TelegramRequest
 * @see BotRegistration
 * @see com.github.darkrymit.telegram.spring.bots.core.Dispatcher
 */
public interface TelegramRequestResolver {

  TelegramRequest resolve(BotRegistration botRegistration, Update update);
}

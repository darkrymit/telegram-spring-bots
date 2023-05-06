package com.github.darkrymit.telegram.spring.bots.core.exception;


import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;

/**
 * Subclass of {@link TelegramException} that indicates that no handler was found for a Telegram update.
 *
 * <p>Typically thrown while processing a Telegram update, to be caught and handled by a registered exceptionHandler.
 */
public class NoHandlerFoundException extends TelegramException{

  private final TelegramRequest telegramRequest;

  public NoHandlerFoundException(String msg, TelegramRequest telegramRequest) {
    super(msg);
    this.telegramRequest = telegramRequest;
  }

  public TelegramRequest getUpdateData(){
    return telegramRequest;
  }

}

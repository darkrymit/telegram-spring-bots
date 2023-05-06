package com.github.darkrymit.telegram.spring.bots.core.exception;


/**
 * Subclass of {@link TelegramException} that indicates that no exception handler was found for a
 * Throwable.
 *
 * <p>Typically thrown while processing a Telegram update exception.
 */
public class NoExceptionHandlerFoundException extends TelegramException {

  public NoExceptionHandlerFoundException(String msg, Throwable throwable) {
    super(msg, throwable);
  }
}

package com.github.darkrymit.telegram.spring.bots.core.exception;

import com.github.darkrymit.telegram.spring.bots.core.Dispatcher;
import org.springframework.lang.Nullable;

/**
 * Subclass of {@link TelegramException} that wraps a nested errors while processing a Telegram update.
 *
 * <p>Typically thrown while processing a Telegram update, to be caught and handled by a registered exceptionHandler.
 *
 * <p>Wrapping nested errors is required to prevent {@link Dispatcher} from 'dying' after errors while processing an update.
 */
public class NestedTelegramException extends TelegramException{

  public NestedTelegramException(String msg) {
    super(msg);
  }

  public NestedTelegramException(@Nullable String msg, @Nullable Throwable cause) {
    super(msg, cause);
  }
}

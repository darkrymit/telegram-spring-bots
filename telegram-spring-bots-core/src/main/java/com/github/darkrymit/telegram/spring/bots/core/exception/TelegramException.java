package com.github.darkrymit.telegram.spring.bots.core.exception;

import org.springframework.lang.Nullable;

public class TelegramException extends Exception{

  public TelegramException(String msg) {
    super(msg);
  }

  public TelegramException(@Nullable String msg, @Nullable Throwable cause) {
    super(msg, cause);
  }
}

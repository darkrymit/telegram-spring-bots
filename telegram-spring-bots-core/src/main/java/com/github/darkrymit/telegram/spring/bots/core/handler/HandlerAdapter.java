package com.github.darkrymit.telegram.spring.bots.core.handler;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.support.ModelAndView;
import org.springframework.lang.Nullable;

/**
 * A strategy interface for invoking chosen handler object for a given update.
 */
public interface HandlerAdapter {

  /**
   * Whether the given handler object is supported by this adapter.
   * @param handler the handler object to check
   * @return {@code true} if this adapter supports the given handler; {@code false} otherwise
   */
  boolean supports(Object handler);

  /**
   * Invoke the handler method with the given event and handler object.
   * @param event the event to handle
   * @param handler the handler object to invoke
   * @return the ModelAndView for the event or {@code null} if the event was handled directly
   * @throws Exception if there is any problem handling the event
   */
  @Nullable
  ModelAndView handle(TelegramRequest event, Object handler) throws Exception;
}


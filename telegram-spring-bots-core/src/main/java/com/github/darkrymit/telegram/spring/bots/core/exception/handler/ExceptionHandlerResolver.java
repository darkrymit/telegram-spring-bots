package com.github.darkrymit.telegram.spring.bots.core.exception.handler;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import org.springframework.lang.Nullable;

/**
 * A strategy interface for resolving the exception handler for a given exception.
 *
 * <p>This is used by a {@link com.github.darkrymit.telegram.spring.bots.core.Dispatcher} to determine the exception handler for an exception.
 * <p>A {@link Object} is then returned for the chosen exception handler, consisting of exception handler object.
 */
public interface ExceptionHandlerResolver {

  /**
   * Resolve the exception handler for the given update.
   *
   * @param event the update data
   * @param handler the handler object if any resolved
   * @param exception the exception to resolve the exception handler for
   * @return the resolved exception handler
   * @throws Exception if the exception handler cannot be resolved
   */
  @Nullable
  Object getExceptionHandler(TelegramRequest event, @Nullable Object handler,Exception exception) throws Exception;
}

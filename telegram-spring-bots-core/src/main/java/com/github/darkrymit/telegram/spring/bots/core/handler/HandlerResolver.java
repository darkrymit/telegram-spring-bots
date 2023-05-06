package com.github.darkrymit.telegram.spring.bots.core.handler;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import org.springframework.lang.Nullable;

/**
 * A strategy interface for resolving the handler for a given update.
 *
 * <p>This is used by a {@link com.github.darkrymit.telegram.spring.bots.core.Dispatcher} to determine the handler for a request.
 * <p>A {@link HandlerExecutionChain} is then returned for the chosen handler, consisting of handler object and any applicable handler interceptors.
 */
public interface HandlerResolver {

  /**
   * Resolve the handler for the given update.
   *
   * @param event the update to resolve the handler for
   * @return the resolved handler execution chain
   * @throws Exception if the handler cannot be resolved
   */
  @Nullable
  HandlerExecutionChain getHandler(TelegramRequest event) throws Exception;
}

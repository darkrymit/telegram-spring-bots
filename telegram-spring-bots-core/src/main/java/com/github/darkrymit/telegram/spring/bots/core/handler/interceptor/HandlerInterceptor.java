package com.github.darkrymit.telegram.spring.bots.core.handler.interceptor;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import org.springframework.lang.Nullable;

/**
 * Interceptor for processing update events before and after they are handled by request handlers.
 *
 * <p>Implementations of this interface can be registered to intercept update events processing and
 * perform additional processing before and after they are handled by the registered request
 * handlers.
 */
public interface HandlerInterceptor {

  /**
   * Called before a request handler is invoked to handle the given update event.
   *
   * @param event   the update event to be handled
   * @param handler the handler that will handle the update event
   * @return {@code true} if the handler should be invoked, or {@code false} to prevent the handler
   * from being invoked
   * @throws Exception in case of errors
   */
  default boolean preHandle(TelegramRequest event, Object handler) throws Exception {
    return true;
  }

  /**
   * Called after the request handler has successfully handled the given update event.
   *
   * @param event   the update event that was handled
   * @param handler the handler that handled the update event
   * @throws Exception in case of errors
   */
  default void postHandle(TelegramRequest event, Object handler) throws Exception {
  }

  /**
   * Called after the request handler has completed processing the given update event, regardless of
   * whether an exception was thrown or not.
   *
   * @param event     the update event that was handled
   * @param handler   the handler that handled the update event
   * @param exception the exception (if any) that was thrown by the handler, or {@code null} if no
   *                  exception was thrown
   * @throws Exception in case of errors
   */
  default void afterCompletion(TelegramRequest event, Object handler, @Nullable Exception exception)
      throws Exception {
  }
}

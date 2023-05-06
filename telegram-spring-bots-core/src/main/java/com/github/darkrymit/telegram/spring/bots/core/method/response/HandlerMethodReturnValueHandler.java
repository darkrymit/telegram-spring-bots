package com.github.darkrymit.telegram.spring.bots.core.method.response;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolver;
import org.springframework.core.MethodParameter;

/**
 * Strategy interface to handle the value returned from the invocation of a handler method .
 *
 * @author Arjen Poutsma
 * @see HandlerMethodArgumentResolver
 * @since 3.1
 */
public interface HandlerMethodReturnValueHandler {

  /**
   * Whether the given {@linkplain MethodParameter method return type} is supported by this
   * handler.
   *
   * @param returnType the method return type to check
   * @return {@code true} if this handler supports the supplied return type; {@code false} otherwise
   */
  boolean supportsReturnType(MethodParameter returnType);

  /**
   * Handle the given return value directly.
   *
   * @param returnValue the value returned from the handler method
   * @param returnType  the type of the return value. This type must have previously been passed to
   *                    {@link #supportsReturnType} which must have returned {@code true}.
   * @param request     the UpdateEvent for the current request
   * @param container  the ModelAndViewContainer for the current request
   * @throws Exception if the return value handling results in an error
   */
  void handleReturnValue(Object returnValue, MethodParameter returnType, TelegramRequest request,
      ModelAndViewContainer container)
      throws Exception;

}
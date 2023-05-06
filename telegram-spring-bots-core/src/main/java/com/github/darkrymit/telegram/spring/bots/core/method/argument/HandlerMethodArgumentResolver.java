package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;

/**
 * Interface for resolving arguments in a handler method. Implementations of this interface must
 * determine whether they can handle the given method parameter and then provide a value for the
 * argument based on the current TelegramRequest.
 */
public interface HandlerMethodArgumentResolver {

  /**
   * Whether the given {@link MethodParameter method parameter} is supported by resolver.
   *
   * @param parameter the method parameter to check
   * @return {@code true} if this resolver can process the supplied parameter; {@code false}
   * otherwise
   */
  boolean supportsParameter(MethodParameter parameter);

  /**
   * Resolves a method parameter into an argument value from the supplied UpdateEvent.
   *
   * @param parameter     the method parameter to resolve
   * @param request       the UpdateEvent containing the request information
   * @param container    the ModelAndViewContainer for the current request
   * @param binderFactory a factory for creating {@link org.springframework.validation.DataBinder} instances
   * @return the resolved argument value, or {@code null} if not resolvable
   * @throws Exception if the resolution fails, e.g. due to a type conversion error
   */
  @Nullable
  Object resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception;
}
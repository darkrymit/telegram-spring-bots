package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.core.MethodParameter;

/**
 * Resolves method parameters by delegating to a list of registered
 * {@link HandlerMethodArgumentResolver} resolvers.
 */
public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

  private final List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
  private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache = new ConcurrentHashMap<>(
      256);

  /**
   * Create a composite resolver for the given {@link HandlerMethodArgumentResolver}s.
   *
   * @param resolvers to add.
   */
  public HandlerMethodArgumentResolverComposite(
      List<? extends HandlerMethodArgumentResolver> resolvers) {
    argumentResolvers.addAll(resolvers);
  }

  public HandlerMethodArgumentResolverComposite addResolver(
      HandlerMethodArgumentResolver resolver) {
    argumentResolvers.add(resolver);
    return this;
  }

  public HandlerMethodArgumentResolverComposite addResolvers(
      List<? extends HandlerMethodArgumentResolver> resolvers) {
    argumentResolvers.addAll(resolvers);
    return this;
  }

  public List<HandlerMethodArgumentResolver> getArgumentResolvers() {
    return Collections.unmodifiableList(argumentResolvers);
  }

  public void clear() {
    this.argumentResolvers.clear();
    this.argumentResolverCache.clear();
  }

  public boolean supportsParameter(MethodParameter parameter) {
    return getArgumentResolver(parameter) != null;
  }

  public Object resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {
    HandlerMethodArgumentResolver resolver = this.getArgumentResolver(parameter);
    if (resolver == null) {
      throw new IllegalArgumentException(
          "Unsupported parameter type [" + parameter.getParameterType().getName() + "]");
    } else {
      return resolver.resolveArgument(parameter, request, container,binderFactory);
    }
  }

  /**
   * Find a registered {@link HandlerMethodArgumentResolver} that supports the given method
   * parameter.
   *
   * @param parameter for which you need to find the argument resolver
   */
  private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
    HandlerMethodArgumentResolver result = argumentResolverCache.get(parameter);
    if (result == null) {
      for (HandlerMethodArgumentResolver resolver : argumentResolvers) {
        if (resolver.supportsParameter(parameter)) {
          result = resolver;
          argumentResolverCache.put(parameter, resolver);
          break;
        }
      }
    }

    return result;
  }
}
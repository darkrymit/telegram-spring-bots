package com.github.darkrymit.telegram.spring.bots.core.exception.handler.method;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.ExceptionHandlerResolver;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMappingInfo;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMappingRegistry;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMethodMappingRegistration;
import com.github.darkrymit.telegram.spring.bots.core.method.HandlerMethod;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

public class ExceptionHandlerMethodResolver implements ExceptionHandlerResolver {

  private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerMethodResolver.class);

  private final ExceptionHandlerMappingRegistry registry;


  public ExceptionHandlerMethodResolver(ExceptionHandlerMappingRegistry registry) {
    this.registry = registry;
  }

  @Override
  @Nullable
  public Object getExceptionHandler(TelegramRequest event, Object handler, Exception exception)
      throws Exception {
    Object exceptionHandler = lookupHandlerMethod(event, handler, exception);
    if (exceptionHandler == null) {
      return null;
    }

    log.debug("Mapped to {}", exceptionHandler);

    return exceptionHandler;
  }

  @Nullable
  protected HandlerMethod lookupHandlerMethod(TelegramRequest event, Object handler,
      Exception exception) {
    for (ExceptionHandlerMappingInfo exceptionHandlerMappingInfo : registry.getSortedMappings()) {
      if (exceptionHandlerMappingInfo.getCondition().isTrue(event, handler, exception)) {
        return ((ExceptionHandlerMethodMappingRegistration) Objects.requireNonNull(
            registry.getRegistration(exceptionHandlerMappingInfo))).getExceptionHandler();

      }
    }
    return null;
  }

}

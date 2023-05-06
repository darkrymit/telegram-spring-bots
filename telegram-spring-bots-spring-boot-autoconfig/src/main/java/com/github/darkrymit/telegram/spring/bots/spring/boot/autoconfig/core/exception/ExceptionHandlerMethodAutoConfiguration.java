package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.exception;

import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.ExceptionHandlerAdapter;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMappingRegistry;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.method.ExceptionHandlerMethodAdapter;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.method.ExceptionHandlerMethodResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.method.response.HandlerMethodReturnValueHandlerComposite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerMethodAutoConfiguration {

  @Bean
  ExceptionHandlerAdapter exceptionHandlerAdapter(HandlerMethodArgumentResolverComposite argumentResolverComposite,
      HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite,
      TelegramDataBinderFactory dataBinderFactory) {
    return new ExceptionHandlerMethodAdapter(argumentResolverComposite, returnValueHandlerComposite,
        dataBinderFactory);
  }

  @Bean
  ExceptionHandlerMethodResolver exceptionHandlerMethodResolver(ExceptionHandlerMappingRegistry registry) {
    return new ExceptionHandlerMethodResolver(registry);
  }
}

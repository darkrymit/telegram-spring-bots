package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.handler;

import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.handler.HandlerAdapter;
import com.github.darkrymit.telegram.spring.bots.core.handler.interceptor.HandlerInterceptor;
import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.HandlerMappingRegistry;
import com.github.darkrymit.telegram.spring.bots.core.handler.method.HandlerMethodAdapter;
import com.github.darkrymit.telegram.spring.bots.core.handler.method.HandlerMethodResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.method.response.HandlerMethodReturnValueHandlerComposite;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerMethodAutoConfiguration {

  @Bean
  HandlerAdapter handlerAdapter(HandlerMethodArgumentResolverComposite argumentResolverComposite,
      HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite,
      TelegramDataBinderFactory dataBinderFactory) {
    return new HandlerMethodAdapter(argumentResolverComposite, returnValueHandlerComposite,
        dataBinderFactory);
  }

  @Bean
  HandlerMethodResolver handlerMethodResolver(HandlerMappingRegistry registry,
      List<HandlerInterceptor> interceptors) {
    return new HandlerMethodResolver(registry, interceptors);
  }
}

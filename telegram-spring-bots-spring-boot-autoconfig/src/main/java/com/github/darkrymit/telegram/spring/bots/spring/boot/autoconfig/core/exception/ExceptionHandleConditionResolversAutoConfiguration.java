package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.exception;

import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleConditionResolver;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleConditionResolverComposite;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandleConditionResolversAutoConfiguration {

  @Bean
  ExceptionHandleConditionResolverComposite exceptionHandleConditionResolverComposite(
      List<ExceptionHandleConditionResolver> resolvers) {
    return new ExceptionHandleConditionResolverComposite(resolvers);
  }
}

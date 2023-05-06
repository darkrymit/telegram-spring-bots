package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.support;

import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleConditionResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMappingRegistry;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleConditionResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.HandlerMappingRegistry;
import com.github.darkrymit.telegram.spring.bots.core.support.ControllerAdviceBeanFactoryPostProcessor;
import com.github.darkrymit.telegram.spring.bots.core.support.TelegramControllerBeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactoryPostProcessorsAutoConfiguration {

  @Bean
  TelegramControllerBeanFactoryPostProcessor telegramControllerBeanFactoryPostProcessor(
      HandlerMappingRegistry registry, HandleConditionResolverComposite resolverComposite,
      ExceptionHandlerMappingRegistry exceptionRegistry,
      ExceptionHandleConditionResolverComposite exceptionConditionResolvers) {
    return new TelegramControllerBeanFactoryPostProcessor(registry, resolverComposite,
        exceptionRegistry, exceptionConditionResolvers);
  }

  @Bean
  ControllerAdviceBeanFactoryPostProcessor controllerAdviceBeanFactoryPostProcessor(
      ExceptionHandlerMappingRegistry exceptionRegistry,
      ExceptionHandleConditionResolverComposite exceptionConditionResolvers) {
    return new ControllerAdviceBeanFactoryPostProcessor(exceptionRegistry,
        exceptionConditionResolvers);
  }
}

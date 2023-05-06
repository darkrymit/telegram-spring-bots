package com.github.darkrymit.telegram.spring.bots.core.support;

import com.github.darkrymit.telegram.spring.bots.core.annotation.ControllerAdvice;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleConditionResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMappingInfo;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMappingRegistry;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMethodMappingRegistration;
import com.github.darkrymit.telegram.spring.bots.core.method.HandlerMethod;
import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class ControllerAdviceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  private final ExceptionHandlerMappingRegistry exceptionRegistry;

  private final ExceptionHandleConditionResolverComposite exceptionConditionResolvers;


  public ControllerAdviceBeanFactoryPostProcessor(ExceptionHandlerMappingRegistry exceptionRegistry,
      ExceptionHandleConditionResolverComposite exceptionConditionResolvers) {
    this.exceptionRegistry = exceptionRegistry;
    this.exceptionConditionResolvers = exceptionConditionResolvers;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    String[] names = beanFactory.getBeanNamesForAnnotation(ControllerAdvice.class);
    for (String name : names) {
      Class<?> beanType = beanFactory.getType(name);
      if (beanType != null) {
        detectExceptionHandlerMethods(name, beanType, beanFactory);
      }

    }
  }

  protected void detectExceptionHandlerMethods(String beanName, Class<?> beanType,
      BeanFactory beanFactory) {
    Map<Method, ExceptionHandlerMappingInfo> methods = FactoryPostProcessorUtils.getExceptionMappingForMethods(
        beanType, exceptionConditionResolvers);
    methods.forEach((method, mapping) -> {
      Method invocableMethod = AopUtils.selectInvocableMethod(method, beanType);
      registerExceptionHandlerMethod(beanName, beanType, beanFactory, mapping, invocableMethod);
    });
  }

  protected void registerExceptionHandlerMethod(String beanName, Class<?> beanType,
      BeanFactory beanFactory, ExceptionHandlerMappingInfo mapping, Method method) {

    HandlerMethod handlerMethod = new HandlerMethod(beanName, beanType, beanFactory, method);

    exceptionRegistry.register(mapping,
        new ExceptionHandlerMethodMappingRegistration(handlerMethod));
  }

}

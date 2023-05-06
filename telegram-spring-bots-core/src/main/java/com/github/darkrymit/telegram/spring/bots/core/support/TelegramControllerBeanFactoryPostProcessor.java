package com.github.darkrymit.telegram.spring.bots.core.support;

import com.github.darkrymit.telegram.spring.bots.core.annotation.TelegramController;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleCondition;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleConditionResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.OnSpecificBeanNameCondition;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMappingInfo;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMappingRegistry;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMethodMappingRegistration;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleConditionResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.HandlerMappingInfo;
import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.HandlerMappingRegistry;
import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.HandlerMethodMappingRegistration;
import com.github.darkrymit.telegram.spring.bots.core.method.HandlerMethod;
import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class TelegramControllerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  private final HandlerMappingRegistry registry;

  private final HandleConditionResolverComposite conditionResolvers;

  private final ExceptionHandlerMappingRegistry exceptionRegistry;

  private final ExceptionHandleConditionResolverComposite exceptionConditionResolvers;


  public TelegramControllerBeanFactoryPostProcessor(HandlerMappingRegistry registry,
      HandleConditionResolverComposite conditionResolvers,
      ExceptionHandlerMappingRegistry exceptionRegistry,
      ExceptionHandleConditionResolverComposite exceptionConditionResolvers) {
    this.registry = registry;
    this.conditionResolvers = conditionResolvers;
    this.exceptionRegistry = exceptionRegistry;
    this.exceptionConditionResolvers = exceptionConditionResolvers;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    String[] names = beanFactory.getBeanNamesForAnnotation(TelegramController.class);
    for (String name : names) {
      Class<?> beanType = beanFactory.getType(name);
      if (beanType != null) {
        detectHandlerMethods(name, beanType, beanFactory);
        detectExceptionHandlerMethods(name, beanType, beanFactory);
      }

    }
  }

  protected void detectHandlerMethods(String beanName, Class<?> beanType, BeanFactory beanFactory) {
    Map<Method, HandlerMappingInfo> methods = FactoryPostProcessorUtils.getMappingForMethods(
        beanType, conditionResolvers);
    methods.forEach((method, mapping) -> {
      Method invocableMethod = AopUtils.selectInvocableMethod(method, beanType);
      registerHandlerMethod(beanName, beanType, beanFactory, mapping, invocableMethod);
    });

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

  protected void registerHandlerMethod(String beanName, Class<?> beanType, BeanFactory beanFactory,
      HandlerMappingInfo mapping, Method method) {
    HandlerMethod handlerMethod = new HandlerMethod(beanName, beanType, beanFactory, method);
    registry.register(mapping, new HandlerMethodMappingRegistration(handlerMethod));
  }

  protected void registerExceptionHandlerMethod(String beanName, Class<?> beanType,
      BeanFactory beanFactory, ExceptionHandlerMappingInfo mapping, Method method) {

    ExceptionHandleCondition transformedCondition = ExceptionHandleCondition.and(
        new OnSpecificBeanNameCondition(beanName), mapping.getCondition());

    ExceptionHandlerMappingInfo transformedMapping = new ExceptionHandlerMappingInfo(
        transformedCondition, mapping.getExecutionPriority());

    HandlerMethod handlerMethod = new HandlerMethod(beanName, beanType, beanFactory, method);

    exceptionRegistry.register(transformedMapping,
        new ExceptionHandlerMethodMappingRegistration(handlerMethod));
  }

}

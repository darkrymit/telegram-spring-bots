package com.github.darkrymit.telegram.spring.bots.core.support;

import com.github.darkrymit.telegram.spring.bots.core.annotation.ExceptionHandlerMapping;
import com.github.darkrymit.telegram.spring.bots.core.annotation.HandlerMapping;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleCondition;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleConditionResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.ExceptionHandlerMappingInfo;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleCondition;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleConditionResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.HandlerMappingInfo;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

public class FactoryPostProcessorUtils {

  private FactoryPostProcessorUtils() {
  }


  @Nullable
  public static HandlerMappingInfo createMappingInfo(AnnotatedElement element,
      HandleConditionResolverComposite conditionResolvers) {

    HandlerMapping handlerMapping = AnnotatedElementUtils.findMergedAnnotation(element,
        HandlerMapping.class);

    HandleCondition condition = null;

    if (conditionResolvers.supports(element)) {
      condition = conditionResolvers.resolveHandleCondition(element);
    }

    if (condition == null && handlerMapping == null) {
      return null;
    }

    condition = condition == null ? HandleCondition.ALWAYS_TRUE : condition;

    int priority = handlerMapping == null ? 0 : handlerMapping.value();

    return new HandlerMappingInfo(condition, priority);
  }

  @Nullable
  public static ExceptionHandlerMappingInfo createExceptionMappingInfo(AnnotatedElement element,
      ExceptionHandleConditionResolverComposite exceptionConditionResolvers) {
    ExceptionHandlerMapping exceptionHandlerMapping = AnnotatedElementUtils.findMergedAnnotation(
        element, ExceptionHandlerMapping.class);

    ExceptionHandleCondition condition = null;

    if (exceptionConditionResolvers.supports(element)) {
      condition = exceptionConditionResolvers.resolveExceptionHandleCondition(element);
    }

    if (condition == null && exceptionHandlerMapping == null) {
      return null;
    }

    condition = condition == null ? ExceptionHandleCondition.ALWAYS_TRUE : condition;

    int priority = exceptionHandlerMapping == null ? 0 : exceptionHandlerMapping.value();

    return new ExceptionHandlerMappingInfo(condition, priority);

  }

  @Nullable
  public static HandlerMappingInfo getMappingForMethod(Method method, Class<?> handlerType,
      HandleConditionResolverComposite conditionResolvers) {
    HandlerMappingInfo info = createMappingInfo(method, conditionResolvers);
    if (info != null) {
      HandlerMappingInfo typeInfo = createMappingInfo(handlerType, conditionResolvers);
      if (typeInfo != null) {
        info = typeInfo.combine(info);
      }
    }
    return info;
  }

  @Nullable
  public static ExceptionHandlerMappingInfo getExceptionMappingForMethod(Method method,
      Class<?> handlerType, ExceptionHandleConditionResolverComposite exceptionConditionResolvers) {
    ExceptionHandlerMappingInfo info = createExceptionMappingInfo(method,
        exceptionConditionResolvers);
    if (info != null) {
      ExceptionHandlerMappingInfo typeInfo = createExceptionMappingInfo(handlerType,
          exceptionConditionResolvers);
      if (typeInfo != null) {
        info = typeInfo.combine(info);
      }
    }
    return info;
  }

  public static Map<Method, HandlerMappingInfo> getMappingForMethods(Class<?> beanType,
      HandleConditionResolverComposite conditionResolvers) {
    return MethodIntrospector.selectMethods(beanType,
        (MethodIntrospector.MetadataLookup<HandlerMappingInfo>) method -> {
          try {
            return getMappingForMethod(method, beanType, conditionResolvers);
          } catch (Exception exception) {
            throw new IllegalStateException(
                "Invalid handle mapping on update handler class [" + beanType.getName() + "]: "
                    + method, exception);
          }
        });
  }


  public static Map<Method, ExceptionHandlerMappingInfo> getExceptionMappingForMethods(
      Class<?> beanType, ExceptionHandleConditionResolverComposite exceptionConditionResolvers) {
    return MethodIntrospector.selectMethods(beanType,
        (MethodIntrospector.MetadataLookup<ExceptionHandlerMappingInfo>) method -> {
          try {
            return getExceptionMappingForMethod(method, beanType, exceptionConditionResolvers);
          } catch (Exception exception) {
            throw new IllegalStateException(
                "Invalid exception mapping on exception handler class [" + beanType.getName()
                    + "]: " + method, exception);
          }
        });
  }


}

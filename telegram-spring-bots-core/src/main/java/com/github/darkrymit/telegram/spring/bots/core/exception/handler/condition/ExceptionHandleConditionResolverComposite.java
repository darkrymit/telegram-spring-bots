package com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

public class ExceptionHandleConditionResolverComposite implements
    ExceptionHandleConditionResolver {

  private final List<ExceptionHandleConditionResolver> resolvers = new ArrayList<>();

  public ExceptionHandleConditionResolverComposite(
      List<? extends ExceptionHandleConditionResolver> resolvers) {
    this.resolvers.addAll(resolvers);
  }

  public ExceptionHandleConditionResolverComposite addResolver(
      ExceptionHandleConditionResolver resolver) {
    resolvers.add(resolver);
    return this;
  }

  public ExceptionHandleConditionResolverComposite addResolvers(
      List<? extends ExceptionHandleConditionResolver> resolvers) {
    this.resolvers.addAll(resolvers);
    return this;
  }

  public List<ExceptionHandleConditionResolver> getResolvers() {
    return resolvers;
  }

  @Override
  public boolean supports(AnnotatedElement element) {
    return anyResolverSupports(element);
  }

  @Override
  public ExceptionHandleCondition resolveExceptionHandleCondition(AnnotatedElement element){
    List<ExceptionHandleCondition> conditions = resolveExceptionHandleConditions(element);
    if (conditions.isEmpty()){
      return null;
    }

    ExceptionHandleCondition mergedCondition = null;

    for (ExceptionHandleCondition condition : conditions) {
      mergedCondition = ExceptionHandleCondition.and(mergedCondition,condition);
    }

    return mergedCondition;
  }

  @Override
  public List<ExceptionHandleCondition> resolveExceptionHandleConditions(AnnotatedElement element) {
    List<ExceptionHandleConditionResolver> supported = getResolvers(element);

    List<ExceptionHandleCondition> conditions = new ArrayList<>();

    if (supported.isEmpty()) {
      return conditions;
    }


    for (ExceptionHandleConditionResolver resolver : supported) {
      conditions.addAll(resolver.resolveExceptionHandleConditions(element));
    }

    conditions.sort(ExceptionHandleCondition.COMPARATOR);

    return conditions;
  }

  private boolean anyResolverSupports(AnnotatedElement element) {
    for (ExceptionHandleConditionResolver resolver : resolvers) {
      if (resolver.supports(element)) {
        return true;
      }
    }
    return false;
  }

  private List<ExceptionHandleConditionResolver> getResolvers(
      AnnotatedElement annotatedElement) {
    List<ExceptionHandleConditionResolver> supported = new ArrayList<>();
    for (ExceptionHandleConditionResolver resolver : resolvers) {
      if (resolver.supports(annotatedElement)) {
        supported.add(resolver);
      }
    }
    return supported;
  }
}

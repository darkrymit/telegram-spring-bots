package com.github.darkrymit.telegram.spring.bots.core.handler.condition;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

public class HandleConditionResolverComposite implements
    HandleConditionResolver {

  private final List<HandleConditionResolver> resolvers = new ArrayList<>();

  public HandleConditionResolverComposite(
      List<? extends HandleConditionResolver> resolvers) {
    this.resolvers.addAll(resolvers);
  }

  public HandleConditionResolverComposite addResolver(
      HandleConditionResolver resolver) {
    resolvers.add(resolver);
    return this;
  }

  public HandleConditionResolverComposite addResolvers(
      List<? extends HandleConditionResolver> resolvers) {
    this.resolvers.addAll(resolvers);
    return this;
  }

  public List<HandleConditionResolver> getResolvers() {
    return resolvers;
  }

  @Override
  public boolean supports(AnnotatedElement element) {
    return anyResolverSupports(element);
  }

  @Override
  public HandleCondition resolveHandleCondition(AnnotatedElement element){
    List<HandleCondition> conditions = resolveHandleConditions(element);
    if (conditions.isEmpty()){
      return null;
    }

    HandleCondition mergedCondition = null;

    for (HandleCondition condition : conditions) {
      mergedCondition = HandleCondition.and(mergedCondition,condition);
    }

    return mergedCondition;
  }

  @Override
  public List<HandleCondition> resolveHandleConditions(AnnotatedElement element) {
    List<HandleConditionResolver> supported = getResolvers(element);

    List<HandleCondition> conditions = new ArrayList<>();

    if (supported.isEmpty()) {
      return conditions;
    }

    for (HandleConditionResolver resolver : supported) {
      conditions.addAll(resolver.resolveHandleConditions(element));
    }

    conditions.sort(HandleCondition.COMPARATOR);

    return conditions;
  }

  private boolean anyResolverSupports(AnnotatedElement element) {
    for (HandleConditionResolver resolver : resolvers) {
      if (resolver.supports(element)) {
        return true;
      }
    }
    return false;
  }

  private List<HandleConditionResolver> getResolvers(
      AnnotatedElement annotatedElement) {
    List<HandleConditionResolver> supported = new ArrayList<>();
    for (HandleConditionResolver resolver : resolvers) {
      if (resolver.supports(annotatedElement)) {
        supported.add(resolver);
      }
    }
    return supported;
  }
}

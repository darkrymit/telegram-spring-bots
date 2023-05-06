package com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;

public class StandardExceptionHandlerMappingRegistry implements ExceptionHandlerMappingRegistry {

  private final Map<ExceptionHandlerMappingInfo, ExceptionHandlerMappingRegistration> registry = new HashMap<>();

  private final List<ExceptionHandlerMappingInfo> sortedMappings = new ArrayList<>();

  @Override
  @Nullable
  public ExceptionHandlerMappingRegistration getRegistration(
      ExceptionHandlerMappingInfo handlerMappingInfo) {
    return registry.get(handlerMappingInfo);
  }

  @Override
  public List<ExceptionHandlerMappingInfo> getSortedMappings() {
    return sortedMappings;
  }

  @Override
  public void register(ExceptionHandlerMappingInfo mapping,
      ExceptionHandlerMappingRegistration handlerRegistration) {
    ExceptionHandlerMappingRegistration old = registry.get(mapping);
    if (old != null) {
      throw new IllegalStateException(
          "Mapping already registered: " + mapping + " old: " + old + " new: "
              + handlerRegistration);
    }
    registry.put(mapping, handlerRegistration);
    sortedMappings.add(mapping);
    Collections.sort(sortedMappings);
  }

  @Override
  public ExceptionHandlerMappingRegistration replace(ExceptionHandlerMappingInfo mapping,
      ExceptionHandlerMappingRegistration handlerRegistration) {
    ExceptionHandlerMappingRegistration old = registry.get(mapping);
    if (old == null) {
      throw new IllegalStateException(
          "Mapping not registered: " + mapping + " new: " + handlerRegistration);
    }
    registry.put(mapping, handlerRegistration);
    return old;
  }
}

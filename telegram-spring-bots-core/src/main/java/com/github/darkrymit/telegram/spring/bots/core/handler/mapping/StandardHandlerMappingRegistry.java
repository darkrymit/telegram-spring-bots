package com.github.darkrymit.telegram.spring.bots.core.handler.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;

public class StandardHandlerMappingRegistry implements HandlerMappingRegistry {

  private final Map<HandlerMappingInfo, HandlerMappingRegistration> registry = new HashMap<>();

  private final List<HandlerMappingInfo> sortedMappings = new ArrayList<>();

  @Override
  @Nullable
  public HandlerMappingRegistration getRegistration(HandlerMappingInfo handlerMappingInfo) {
    return registry.get(handlerMappingInfo);
  }

  @Override
  public List<HandlerMappingInfo> getSortedMappings() {
    return sortedMappings;
  }

  @Override
  public void register(HandlerMappingInfo mapping, HandlerMappingRegistration handlerRegistration) {
    HandlerMappingRegistration old = registry.get(mapping);
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
  public HandlerMappingRegistration replace(HandlerMappingInfo mapping,
      HandlerMappingRegistration handlerRegistration) {
    HandlerMappingRegistration old = registry.get(mapping);
    if (old == null) {
      throw new IllegalStateException(
          "Mapping not registered: " + mapping + " new: " + handlerRegistration);
    }
    registry.put(mapping, handlerRegistration);
    return old;
  }
}

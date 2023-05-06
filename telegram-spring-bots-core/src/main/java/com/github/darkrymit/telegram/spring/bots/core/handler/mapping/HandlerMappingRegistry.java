package com.github.darkrymit.telegram.spring.bots.core.handler.mapping;

import java.util.List;
import org.springframework.lang.Nullable;

/**
 * A registry for mapping handlers, allowing to retrieve and register mapping information along with
 * the corresponding handler registration.
 */
public interface HandlerMappingRegistry {

  /**
   * Get the mapping handler registration for the given mapping information.
   *
   * @param handlerMappingInfo the mapping information
   * @return the mapping handler registration or {@code null} if none found
   */
  @Nullable
  HandlerMappingRegistration getRegistration(HandlerMappingInfo handlerMappingInfo);

  /**
   * Get all the registered mapping information sorted.
   *
   * @return the sorted list of mapping information
   */
  List<HandlerMappingInfo> getSortedMappings();

  /**
   * Register a {@link HandlerMappingRegistration} for the given {@link HandlerMappingInfo}.
   *
   * @param mapping             the mapping info to register
   * @param handlerRegistration the handler registration to associate with the mapping
   * @throws IllegalStateException if a registration already exists for {@link HandlerMappingInfo}
   */
  void register(HandlerMappingInfo mapping, HandlerMappingRegistration handlerRegistration);

  /**
   * Replaces the existing {@link HandlerMappingRegistration} for the given
   * {@link HandlerMappingInfo} with the provided one.
   *
   * @param mapping             the mapping info to replace the registration for
   * @param handlerRegistration the new handler registration to associate with the mapping
   * @return the previous handler registration, if one existed
   * @throws IllegalStateException if no registration exists for the given
   *                               {@link HandlerMappingInfo}
   */
  HandlerMappingRegistration replace(HandlerMappingInfo mapping,
      HandlerMappingRegistration handlerRegistration);
}


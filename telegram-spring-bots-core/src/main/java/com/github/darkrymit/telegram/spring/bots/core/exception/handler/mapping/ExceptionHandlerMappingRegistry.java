package com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping;

import java.util.List;
import org.springframework.lang.Nullable;

/**
 * A registry for mapping exception handlers, allowing to retrieve and register mapping information
 * along with the corresponding exception handler registration.
 */
public interface ExceptionHandlerMappingRegistry {

  /**
   * Get the mapping exception handler registration for the given mapping information.
   *
   * @param mappingInfo the mapping information
   * @return the mapping exception handler registration or {@code null} if none found
   */
  @Nullable
  ExceptionHandlerMappingRegistration getRegistration(ExceptionHandlerMappingInfo mappingInfo);

  /**
   * Get all the registered mapping information sorted.
   *
   * @return the sorted list of mapping information
   */
  List<ExceptionHandlerMappingInfo> getSortedMappings();

  /**
   * Register a {@link ExceptionHandlerMappingRegistration} for the given
   * {@link ExceptionHandlerMappingInfo}.
   *
   * @param mappingInfo  the mapping info to register
   * @param registration the exception handler registration to associate with the mapping
   * @throws IllegalStateException if a registration already exists for
   *                               {@link ExceptionHandlerMappingInfo}
   */
  void register(ExceptionHandlerMappingInfo mappingInfo,
      ExceptionHandlerMappingRegistration registration);

  /**
   * Replaces the existing {@link ExceptionHandlerMappingRegistration} for the given
   * {@link ExceptionHandlerMappingInfo} with the provided one.
   *
   * @param mappingInfo  the mapping info to replace the registration for
   * @param registration the new exception handler registration to associate with the mapping
   * @return the previous exception handler registration, if one existed
   * @throws IllegalStateException if no registration exists for the given
   *                               {@link ExceptionHandlerMappingInfo}
   */
  ExceptionHandlerMappingRegistration replace(ExceptionHandlerMappingInfo mappingInfo,
      ExceptionHandlerMappingRegistration registration);
}


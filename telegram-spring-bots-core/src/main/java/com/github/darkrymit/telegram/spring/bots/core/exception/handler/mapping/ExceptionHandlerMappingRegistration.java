package com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping;

/**
 * Represents the registration of an exception handler.
 */
public interface ExceptionHandlerMappingRegistration {

  /**
   * Returns the exception handler.
   *
   * @return exception handler
   */
  Object getExceptionHandler();
}


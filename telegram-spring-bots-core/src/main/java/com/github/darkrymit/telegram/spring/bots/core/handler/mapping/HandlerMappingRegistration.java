package com.github.darkrymit.telegram.spring.bots.core.handler.mapping;

/**
 * Represents the registration of a handler.
 */
public interface HandlerMappingRegistration {

  /**
   * Returns the handler.
   *
   * @return handler
   */
  Object getHandler();
}


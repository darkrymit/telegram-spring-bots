package com.github.darkrymit.telegram.spring.bots.core.bind;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import org.springframework.validation.DataBinder;

public class DefaultDataBinderFactory implements TelegramDataBinderFactory{

  private final TelegramDataBinderInitializer initializer;

  public DefaultDataBinderFactory(TelegramDataBinderInitializer initializer) {
    this.initializer = initializer;
  }

  @Override
  public DataBinder createBinder(TelegramRequest request, Object target, String name)
      throws Exception {
    DataBinder dataBinder = new DataBinder(target, name);
    initializer.initBinder(dataBinder);
    return dataBinder;
  }
}

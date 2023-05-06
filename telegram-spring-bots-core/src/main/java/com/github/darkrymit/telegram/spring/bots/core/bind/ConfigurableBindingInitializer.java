package com.github.darkrymit.telegram.spring.bots.core.bind;

import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

public class ConfigurableBindingInitializer implements TelegramDataBinderInitializer{

  private final Validator validator;

  public ConfigurableBindingInitializer(Validator validator) {
    this.validator = validator;
  }

  @Override
  public void initBinder(DataBinder binder) {
    binder.setValidator(validator);
  }
}

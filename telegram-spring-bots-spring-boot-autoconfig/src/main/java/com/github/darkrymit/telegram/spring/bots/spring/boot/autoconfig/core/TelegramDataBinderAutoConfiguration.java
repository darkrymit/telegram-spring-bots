package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core;

import com.github.darkrymit.telegram.spring.bots.core.bind.ConfigurableBindingInitializer;
import com.github.darkrymit.telegram.spring.bots.core.bind.DefaultDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

@Configuration
public class TelegramDataBinderAutoConfiguration {

  @Bean
  public ConfigurableBindingInitializer telegramDataBinderInitializer(Validator validator) {
    return new ConfigurableBindingInitializer(validator);
  }

  @Bean
  public DefaultDataBinderFactory defaultDataBinderFactory(TelegramDataBinderInitializer initializer) {
    return new DefaultDataBinderFactory(initializer);
  }
}

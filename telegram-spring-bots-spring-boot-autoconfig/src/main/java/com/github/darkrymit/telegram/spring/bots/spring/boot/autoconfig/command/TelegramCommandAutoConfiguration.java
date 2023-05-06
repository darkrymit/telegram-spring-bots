package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.command;

import com.github.darkrymit.telegram.spring.bots.command.condition.CommandConditionResolver;
import com.github.darkrymit.telegram.spring.bots.command.method.CommandBodyArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.command.method.CommandNameArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramCommandAutoConfiguration {

  @Bean
  public CommandConditionResolver commandConditionResolver() {
    return new CommandConditionResolver();
  }

  @Bean
  public CommandNameArgumentResolver commandArgumentResolver() {
    return new CommandNameArgumentResolver();
  }

  @Bean
  public CommandBodyArgumentResolver commandBodyArgumentResolver() {
    return new CommandBodyArgumentResolver();
  }
}

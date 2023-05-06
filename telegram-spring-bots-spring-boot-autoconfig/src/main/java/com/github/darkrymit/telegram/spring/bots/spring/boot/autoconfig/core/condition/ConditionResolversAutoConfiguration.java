package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.condition;

import com.github.darkrymit.telegram.spring.bots.core.condition.UpdateTypeConditionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionResolversAutoConfiguration {

  @Bean
  public UpdateTypeConditionResolver updateTypeConditionResolver() {
    return new UpdateTypeConditionResolver();
  }
}

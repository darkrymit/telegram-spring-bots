package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.session;

import com.github.darkrymit.telegram.spring.bots.core.session.user.InMemoryCachedUserSessionResolver;
import com.github.darkrymit.telegram.spring.bots.core.session.user.UserSessionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserSessionAutoConfiguration {

  @Bean
  public UserSessionResolver userSessionResolver() {
    return new InMemoryCachedUserSessionResolver(10000);
  }
}

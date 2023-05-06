package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.session;

import com.github.darkrymit.telegram.spring.bots.core.session.chat.ChatSessionResolver;
import com.github.darkrymit.telegram.spring.bots.core.session.chat.InMemoryCachedChatSessionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatSessionAutoConfiguration {

  @Bean
  public ChatSessionResolver chatSessionResolver() {
    return new InMemoryCachedChatSessionResolver(10000);
  }
}

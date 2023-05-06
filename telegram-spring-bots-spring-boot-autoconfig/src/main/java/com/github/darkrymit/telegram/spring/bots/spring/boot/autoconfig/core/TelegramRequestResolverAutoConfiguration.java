package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core;

import com.github.darkrymit.telegram.spring.bots.core.DefaultTelegramRequestResolver;
import com.github.darkrymit.telegram.spring.bots.core.TelegramRequestResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramRequestResolverAutoConfiguration {

   @Bean
   TelegramRequestResolver updateEventResolver() {
        return new DefaultTelegramRequestResolver();
   }
}

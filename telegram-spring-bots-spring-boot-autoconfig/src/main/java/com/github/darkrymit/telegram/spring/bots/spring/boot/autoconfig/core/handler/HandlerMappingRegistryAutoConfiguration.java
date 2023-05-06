package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.handler;

import com.github.darkrymit.telegram.spring.bots.core.handler.mapping.StandardHandlerMappingRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerMappingRegistryAutoConfiguration {

    @Bean
    StandardHandlerMappingRegistry standardMappingHandlersRegistry() {
        return new StandardHandlerMappingRegistry();
    }

}

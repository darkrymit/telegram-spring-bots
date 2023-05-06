package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.exception;

import com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping.StandardExceptionHandlerMappingRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerMappingRegistryAutoConfiguration {

    @Bean
    StandardExceptionHandlerMappingRegistry standardExceptionHandlerMappingRegistry() {
        return new StandardExceptionHandlerMappingRegistry();
    }

}

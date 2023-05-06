package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.handler;

import com.github.darkrymit.telegram.spring.bots.core.handler.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerInterceptorAutoConfiguration {

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }
}

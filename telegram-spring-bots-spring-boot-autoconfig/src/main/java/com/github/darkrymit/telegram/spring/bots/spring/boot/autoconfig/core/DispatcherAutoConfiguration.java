package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core;

import com.github.darkrymit.telegram.spring.bots.core.BotRegistration;
import com.github.darkrymit.telegram.spring.bots.core.Dispatcher;
import com.github.darkrymit.telegram.spring.bots.core.TelegramRequestResolver;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.ExceptionHandlerAdapter;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.ExceptionHandlerResolver;
import com.github.darkrymit.telegram.spring.bots.core.handler.HandlerAdapter;
import com.github.darkrymit.telegram.spring.bots.core.handler.HandlerResolver;
import com.pengrad.telegrambot.TelegramBot;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * AutoConfiguration for {@link Dispatcher} bean creation.
 */
@Configuration
public class DispatcherAutoConfiguration {

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    Dispatcher dispatcher(List<BotRegistration> bots, TelegramRequestResolver resolver, HandlerResolver handlerResolver, HandlerAdapter handlerAdapter,
        ExceptionHandlerResolver exceptionHandlerResolver, ExceptionHandlerAdapter exceptionHandlerAdapter) {
        return new Dispatcher(bots,resolver, handlerResolver, handlerAdapter,
            exceptionHandlerResolver, exceptionHandlerAdapter);
    }

    @Configuration
    static class LifecycleAutoConfiguration {

        @Bean
        ApplicationListener<ContextRefreshedEvent> onContextRefreshed(Dispatcher dispatcher) {
            return event -> dispatcher.start();
        }

        @Bean
        ApplicationListener<ContextClosedEvent> onContextClosed(Dispatcher dispatcher) {
            return event -> dispatcher.stop();
        }

    }
}

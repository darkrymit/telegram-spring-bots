package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.method;

import com.github.darkrymit.telegram.spring.bots.core.method.response.BaseRequestReturnValueHandler;
import com.github.darkrymit.telegram.spring.bots.core.method.response.HandlerMethodReturnValueHandler;
import com.github.darkrymit.telegram.spring.bots.core.method.response.HandlerMethodReturnValueHandlerComposite;
import com.github.darkrymit.telegram.spring.bots.core.method.response.ViewNameReturnValueHandler;
import com.github.darkrymit.telegram.spring.bots.core.method.response.ViewReturnValueHandler;
import com.github.darkrymit.telegram.spring.bots.core.method.response.VoidReturnValueHandler;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerMethodReturnValueHandlersAutoConfiguration {

    @Bean
    public HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite(List<HandlerMethodReturnValueHandler> handlers) {
        return new HandlerMethodReturnValueHandlerComposite(handlers);
    }

    @Bean
    public VoidReturnValueHandler voidHandlerMethodReturnValueHandler() {
        return new VoidReturnValueHandler();
    }

    @Bean
    public BaseRequestReturnValueHandler baseRequestHandlerMethodReturnValueHandler() {
        return new BaseRequestReturnValueHandler();
    }

    @Bean
    public ViewReturnValueHandler viewHandlerMethodReturnValueHandler() {
        return new ViewReturnValueHandler();
    }

    @Bean
    public ViewNameReturnValueHandler viewNameHandlerMethodReturnValueHandler() {
        return new ViewNameReturnValueHandler();
    }

}

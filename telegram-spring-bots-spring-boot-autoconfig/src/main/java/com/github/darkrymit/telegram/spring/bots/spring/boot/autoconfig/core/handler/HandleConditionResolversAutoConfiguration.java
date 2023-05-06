package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.handler;

import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleConditionResolver;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleConditionResolverComposite;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandleConditionResolversAutoConfiguration {

    @Bean
    HandleConditionResolverComposite updateDataHandleConditionResolverComposite(List<HandleConditionResolver> resolvers) {
        return new HandleConditionResolverComposite(resolvers);
    }


}

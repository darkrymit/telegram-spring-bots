package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.spel;

import com.github.darkrymit.telegram.spring.bots.spel.condition.SpELConditionResolver;
import com.github.darkrymit.telegram.spring.bots.spel.method.SpELArgumentResolver;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@Configuration
public class TelegramSpELAutoConfiguration {

  @Bean
  ExpressionParser telegramSpELExpressionParser() {
    return new SpelExpressionParser();
  }

  @Bean
  BeanFactoryResolver telegramSpELBeanFactoryResolver(BeanFactory beanFactory) {
    return new BeanFactoryResolver(beanFactory);
  }

  @Bean
  EvaluationContext telegramSpELEvaluationContext(
      @Qualifier("telegramSpELBeanFactoryResolver") BeanFactoryResolver beanFactoryResolver) {
    StandardEvaluationContext context = new StandardEvaluationContext();
    context.setBeanResolver(beanFactoryResolver);
    return context;
  }

  @Bean
  SpELConditionResolver spELConditionResolver(
      @Qualifier("telegramSpELEvaluationContext") EvaluationContext evaluationContext,
      @Qualifier("telegramSpELExpressionParser") ExpressionParser expressionParser) {
    return new SpELConditionResolver(evaluationContext, expressionParser);
  }

  @Bean
  SpELArgumentResolver spELArgumentResolver(
      @Qualifier("telegramSpELEvaluationContext") EvaluationContext evaluationContext,
      @Qualifier("telegramSpELExpressionParser") ExpressionParser expressionParser) {
    return new SpELArgumentResolver(evaluationContext, expressionParser);
  }
}

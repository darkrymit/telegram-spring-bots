package com.github.darkrymit.telegram.spring.bots.spring.boot.autoconfig.core.method;

import com.github.darkrymit.telegram.spring.bots.core.method.argument.CallbackQueryArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.ChatArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.ChatJoinRequestArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.ChatMemberUpdatedArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.ChatSessionArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.ChosenInlineResultArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.InlineQueryArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.MessageArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.ModelMapArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.PollAnswerArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.PollArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.PreCheckoutQueryArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.ShippingQueryArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.TelegramRequestArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.UserArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.UserSessionArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.core.session.chat.ChatSessionResolver;
import com.github.darkrymit.telegram.spring.bots.core.session.user.UserSessionResolver;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerMethodArgumentResolversAutoConfiguration {

  @Bean
  public CallbackQueryArgumentResolver callbackQueryArgumentResolver() {
    return new CallbackQueryArgumentResolver();
  }

  @Bean
  public ChatJoinRequestArgumentResolver chatJoinRequestArgumentResolver() {
    return new ChatJoinRequestArgumentResolver();
  }

  @Bean
  public ChatMemberUpdatedArgumentResolver chatMemberUpdatedArgumentResolver() {
    return new ChatMemberUpdatedArgumentResolver();
  }

  @Bean
  public ChosenInlineResultArgumentResolver chosenInlineResultArgumentResolver() {
    return new ChosenInlineResultArgumentResolver();
  }

  @Bean
  public HandlerMethodArgumentResolverComposite argumentResolverComposite(
      List<HandlerMethodArgumentResolver> resolvers) {
    return new HandlerMethodArgumentResolverComposite(resolvers);
  }

  @Bean
  public InlineQueryArgumentResolver inlineQueryArgumentResolver() {
    return new InlineQueryArgumentResolver();
  }

  @Bean
  public MessageArgumentResolver messageHandlerMethodArgumentResolver() {
    return new MessageArgumentResolver();
  }

  @Bean
  public ModelMapArgumentResolver modelArgumentResolver() {
    return new ModelMapArgumentResolver();
  }

  @Bean
  public PollAnswerArgumentResolver pollAnswerArgumentResolver() {
    return new PollAnswerArgumentResolver();
  }

  @Bean
  public PollArgumentResolver pollArgumentResolver() {
    return new PollArgumentResolver();
  }

  @Bean
  public PreCheckoutQueryArgumentResolver preCheckoutQueryArgumentResolver() {
    return new PreCheckoutQueryArgumentResolver();
  }

  @Bean
  public ShippingQueryArgumentResolver shippingQueryArgumentResolver() {
    return new ShippingQueryArgumentResolver();
  }

  @Bean
  public TelegramRequestArgumentResolver updateEventHandlerMethodArgumentResolver() {
    return new TelegramRequestArgumentResolver();
  }

  @Bean
  public UserSessionArgumentResolver userSessionArgumentResolver(
      UserSessionResolver userSessionResolver) {
    return new UserSessionArgumentResolver(userSessionResolver);
  }

  @Bean
  public ChatSessionArgumentResolver chatSessionArgumentResolver(
      ChatSessionResolver chatSessionResolver) {
    return new ChatSessionArgumentResolver(chatSessionResolver);
  }

  @Bean
  public UserArgumentResolver userArgumentResolver() {
    return new UserArgumentResolver();
  }

  @Bean
  public ChatArgumentResolver chatArgumentResolver() {
    return new ChatArgumentResolver();
  }

}

package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.github.darkrymit.telegram.spring.bots.core.session.user.UserSession;
import com.github.darkrymit.telegram.spring.bots.core.session.user.UserSessionResolver;
import com.pengrad.telegrambot.model.User;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;

public class UserSessionArgumentResolver implements HandlerMethodArgumentResolver {

  private final UserSessionResolver userSessionResolver;

  public UserSessionArgumentResolver(UserSessionResolver userSessionResolver) {
    this.userSessionResolver = userSessionResolver;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return UserSession.class.isAssignableFrom(parameter.getParameterType())
        || UserSessionResolver.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  @Nullable
  public Object resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {
    User user = request.getUser();
    if (user == null) {
      return null;
    }
    if (UserSessionResolver.class.isAssignableFrom(parameter.getParameterType())) {
      return userSessionResolver;
    }
    return userSessionResolver.getOrCreate(user.id());
  }
}

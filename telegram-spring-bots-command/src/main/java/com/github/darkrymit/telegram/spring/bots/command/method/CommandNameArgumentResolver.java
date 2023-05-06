package com.github.darkrymit.telegram.spring.bots.command.method;

import com.github.darkrymit.telegram.spring.bots.command.CommandName;
import com.github.darkrymit.telegram.spring.bots.command.support.CommandUtils;
import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;

public class CommandNameArgumentResolver implements HandlerMethodArgumentResolver {


  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return String.class.isAssignableFrom(parameter.getParameterType())
        && AnnotatedElementUtils.hasAnnotation(parameter.getParameter(), CommandName.class);
  }

  @Override
  public String resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {
    return CommandUtils.extractCommandName(request.getUpdate().message());
  }

}

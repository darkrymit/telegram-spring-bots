package com.github.darkrymit.telegram.spring.bots.core.method.argument;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.bind.annotation.ModelAttributes;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

public class ModelMapArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Model.class.isAssignableFrom(parameter.getParameterType())
        || ModelMap.class.isAssignableFrom(parameter.getParameterType())
        || (Map.class.isAssignableFrom(parameter.getParameterType()) && parameter
            .hasParameterAnnotation(ModelAttributes.class));
  }

  @Override
  public ExtendedModelMap resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) throws Exception {
    return container.getModel();
  }
}

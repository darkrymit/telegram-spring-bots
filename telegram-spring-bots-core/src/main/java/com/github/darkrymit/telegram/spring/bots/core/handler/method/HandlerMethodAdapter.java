package com.github.darkrymit.telegram.spring.bots.core.handler.method;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.handler.HandlerAdapter;
import com.github.darkrymit.telegram.spring.bots.core.method.HandlerMethod;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.github.darkrymit.telegram.spring.bots.core.method.TelegramRequestInvocableHandlerMethod;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.method.response.HandlerMethodReturnValueHandlerComposite;
import com.github.darkrymit.telegram.spring.bots.core.support.ModelAndView;
import com.github.darkrymit.telegram.spring.bots.core.support.ModelAndViewUtils;
import org.springframework.lang.Nullable;

public class HandlerMethodAdapter implements HandlerAdapter {

  private final HandlerMethodArgumentResolverComposite argumentResolvers;
  private final HandlerMethodReturnValueHandlerComposite returnValueHandler;
  private final TelegramDataBinderFactory dataBinderFactory;

  public HandlerMethodAdapter(HandlerMethodArgumentResolverComposite argumentResolvers,
      HandlerMethodReturnValueHandlerComposite returnValueHandler,
      TelegramDataBinderFactory dataBinderFactory) {
    this.argumentResolvers = argumentResolvers;
    this.returnValueHandler = returnValueHandler;
    this.dataBinderFactory = dataBinderFactory;
  }

  @Override
  public boolean supports(Object handler) {
    return handler instanceof HandlerMethod;
  }

  @Override
  public ModelAndView handle(TelegramRequest event, Object handler) throws Exception {
    return invokeHandlerMethod(event, (HandlerMethod) handler);
  }

  @Nullable
  private ModelAndView invokeHandlerMethod(TelegramRequest event, HandlerMethod handler) throws Exception {
    TelegramRequestInvocableHandlerMethod invocableMethod = createInvocableHandlerMethod(handler);
    ModelAndViewContainer mavContainer = new ModelAndViewContainer();
    invocableMethod.invokeAndHandle(event,mavContainer);

    return ModelAndViewUtils.getModelAndView(mavContainer);

  }

  private TelegramRequestInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handler) {
    HandlerMethod handlerMethod = handler.createWithResolvedBean();
    return new TelegramRequestInvocableHandlerMethod(handlerMethod, argumentResolvers, returnValueHandler,
        dataBinderFactory);
  }

}

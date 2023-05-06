package com.github.darkrymit.telegram.spring.bots.core.exception.handler.method;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.ExceptionHandlerAdapter;
import com.github.darkrymit.telegram.spring.bots.core.method.HandlerMethod;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.github.darkrymit.telegram.spring.bots.core.method.TelegramRequestInvocableHandlerMethod;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.method.response.HandlerMethodReturnValueHandlerComposite;
import com.github.darkrymit.telegram.spring.bots.core.support.ModelAndView;
import com.github.darkrymit.telegram.spring.bots.core.support.ModelAndViewUtils;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

public class ExceptionHandlerMethodAdapter implements ExceptionHandlerAdapter {

  private final HandlerMethodArgumentResolverComposite argumentResolvers;
  private final HandlerMethodReturnValueHandlerComposite returnValueHandler;
  private final TelegramDataBinderFactory dataBinderFactory;

  public ExceptionHandlerMethodAdapter(HandlerMethodArgumentResolverComposite argumentResolvers,
      HandlerMethodReturnValueHandlerComposite returnValueHandler,
      TelegramDataBinderFactory dataBinderFactory) {
    this.argumentResolvers = argumentResolvers;
    this.returnValueHandler = returnValueHandler;
    this.dataBinderFactory = dataBinderFactory;
  }

  @Override
  public boolean supports(Object exceptionHandler) {
    return exceptionHandler instanceof HandlerMethod;
  }

  @Override
  @Nullable
  public ModelAndView handleException(TelegramRequest event,@Nullable Object handler,Exception exception,Object exceptionHandler) throws Exception {
    return invokeHandlerMethod(event, handler , exception, (HandlerMethod) exceptionHandler);
  }

  @Nullable
  private ModelAndView invokeHandlerMethod(TelegramRequest event,@Nullable Object handler,Exception exception,HandlerMethod exceptionHandler) throws Exception {
    TelegramRequestInvocableHandlerMethod invocableMethod = createInvocableHandlerMethod(exceptionHandler);

    Object[] arguments = getProvidedArguments(handler, exception);

    ModelAndViewContainer mavContainer = new ModelAndViewContainer();
    invocableMethod.invokeAndHandle(event,mavContainer,arguments);

    return ModelAndViewUtils.getModelAndView(mavContainer);

  }

  @NotNull
  private static Object[] getProvidedArguments(@Nullable Object handler, Exception exception) {
    ArrayList<Throwable> exceptions = new ArrayList<>();

    Throwable currentException = exception;
    while (currentException != null) {
      exceptions.add(currentException);
      Throwable cause = currentException.getCause();
      currentException = (cause != currentException ? cause : null);
    }

    Object[] arguments = new Object[exceptions.size() + 1];
    exceptions.toArray(arguments);  // efficient arraycopy call in ArrayList
    arguments[arguments.length - 1] = handler;
    return arguments;
  }


  private TelegramRequestInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handler) {
    HandlerMethod handlerMethod = handler.createWithResolvedBean();
    return new TelegramRequestInvocableHandlerMethod(handlerMethod, argumentResolvers, returnValueHandler,
        dataBinderFactory);
  }

}

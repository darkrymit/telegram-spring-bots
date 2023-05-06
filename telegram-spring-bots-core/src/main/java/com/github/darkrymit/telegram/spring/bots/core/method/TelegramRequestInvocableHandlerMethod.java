package com.github.darkrymit.telegram.spring.bots.core.method;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolverComposite;
import com.github.darkrymit.telegram.spring.bots.core.method.response.HandlerMethodReturnValueHandlerComposite;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

@Getter
@Slf4j
public class TelegramRequestInvocableHandlerMethod extends HandlerMethod {

  private static final Object[] EMPTY_ARGS = new Object[0];
  private final HandlerMethodArgumentResolverComposite argumentResolvers;
  private final HandlerMethodReturnValueHandlerComposite returnValueHandlers;
  private final TelegramDataBinderFactory dataBinderFactory;
  private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

  public TelegramRequestInvocableHandlerMethod(HandlerMethod handlerMethod,
      HandlerMethodArgumentResolverComposite argumentResolvers,
      HandlerMethodReturnValueHandlerComposite returnValueHandlers,
      TelegramDataBinderFactory dataBinderFactory) {
    super(handlerMethod);
    this.argumentResolvers = argumentResolvers;
    this.returnValueHandlers = returnValueHandlers;
    this.dataBinderFactory = dataBinderFactory;
  }

  public Object invokeForRequest(TelegramRequest request, ModelAndViewContainer container,
      Object... providedArguments) throws Exception {
    Object[] args = getMethodArgumentValues(request, container, providedArguments);
    log.trace("Arguments: " + Arrays.toString(args));
    return doInvoke(args);
  }

  public void invokeAndHandle(TelegramRequest event, ModelAndViewContainer container,
      Object... providedArguments) throws Exception {
    Object returnValue = invokeForRequest(event, container, providedArguments);

    returnValueHandlers.handleReturnValue(returnValue, getReturnValueType(returnValue), event,container);

  }

  protected Object[] getMethodArgumentValues(TelegramRequest request,
      ModelAndViewContainer container, Object... providedArguments) throws Exception {
    MethodParameter[] parameters = getMethodParameters();
    if (ObjectUtils.isEmpty(parameters)) {
      return EMPTY_ARGS;
    }

    Object[] args = new Object[parameters.length];

    for (int i = 0; i < parameters.length; ++i) {
      MethodParameter parameter = parameters[i];
      parameter.initParameterNameDiscovery(parameterNameDiscoverer);
      args[i] = findProvidedArgument(parameter, providedArguments);
      if (args[i] != null) {
        continue;
      }
      if (!argumentResolvers.supportsParameter(parameter)) {
        throw new IllegalStateException(formatArgumentError(parameter, "No suitable resolver"));
      }

      args[i] = argumentResolvers.resolveArgument(parameter, request, container,dataBinderFactory);

    }

    return args;

  }

  @Nullable
  protected Object findProvidedArgument(MethodParameter parameter, Object... providedArgs) {
    if (ObjectUtils.isEmpty(providedArgs)) {
      return null;
    }

    for (Object providedArg : providedArgs) {
      if (parameter.getParameterType().isInstance(providedArg)) {
        return providedArg;
      }
    }

    return null;
  }

  protected Object doInvoke(Object... args) throws Exception {
    Method method = getBridgedMethod();

    try {
      return method.invoke(getBean(), args);
    } catch (IllegalArgumentException var5) {
      assertTargetBean(method, getBean(), args);
      String text = var5.getMessage() != null ? var5.getMessage() : "Illegal argument";
      throw new IllegalStateException(this.formatInvokeError(text, args), var5);
    } catch (InvocationTargetException var6) {
      Throwable targetException = var6.getTargetException();
      if (targetException instanceof RuntimeException) {
        throw (RuntimeException) targetException;
      } else if (targetException instanceof Error) {
        throw (Error) targetException;
      } else if (targetException instanceof Exception) {
        throw (Exception) targetException;
      } else {
        throw new IllegalStateException(this.formatInvokeError("Invocation failure", args),
            targetException);
      }
    }
  }
}

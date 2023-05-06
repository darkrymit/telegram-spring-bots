package com.github.darkrymit.telegram.spring.bots.core.method;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class HandlerMethod {

  private final String beanName;

  @Nullable
  private final Object bean;

  private final BeanFactory beanFactory;

  private final Class<?> beanType;

  private final Method method;

  private final Method bridgedMethod;

  private final MethodParameter[] parameters;

  public HandlerMethod(String beanName, Class<?> beanType, BeanFactory beanFactory, Method method) {
    Assert.hasText(beanName, "Bean name is required");
    Assert.notNull(beanType, "Bean type is required");
    Assert.notNull(beanFactory, "BeanFactory is required");
    Assert.notNull(method, "Method is required");
    this.beanName = beanName;
    this.bean = null;
    this.beanFactory = beanFactory;
    this.beanType = beanType;
    this.method = method;
    this.bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
    ReflectionUtils.makeAccessible(bridgedMethod);
    this.parameters = initMethodParameters();
  }

  protected HandlerMethod(HandlerMethod handlerMethod) {
    Assert.notNull(handlerMethod, "HandlerMethod is required");
    this.beanName = handlerMethod.beanName;
    this.bean = handlerMethod.bean;
    this.beanFactory = handlerMethod.beanFactory;
    this.beanType = handlerMethod.beanType;
    this.method = handlerMethod.method;
    this.bridgedMethod = handlerMethod.bridgedMethod;
    this.parameters = handlerMethod.parameters;
  }

  public HandlerMethod(HandlerMethod handlerMethod, Object bean) {
    Assert.notNull(handlerMethod, "HandlerMethod is required");
    Assert.notNull(bean, "Bean is required");
    this.beanName = handlerMethod.beanName;
    this.bean = bean;
    this.beanFactory = handlerMethod.beanFactory;
    this.beanType = bean.getClass();
    this.method = handlerMethod.method;
    this.bridgedMethod = handlerMethod.bridgedMethod;
    this.parameters = handlerMethod.parameters;
  }

  protected static String formatArgumentError(MethodParameter param, String message) {
    return "Could not resolve parameter [" + param.getParameterIndex() + "] in "
        + param.getExecutable().toGenericString() + (StringUtils.hasText(message) ? ": " + message
        : "");
  }

  private MethodParameter[] initMethodParameters() {
    int count = bridgedMethod.getParameterCount();
    MethodParameter[] result = new MethodParameter[count];

    for (int i = 0; i < count; ++i) {
      result[i] = new MethodParameter(bridgedMethod, i);
    }

    return result;
  }

  public String getBeanName() {
    return beanName;
  }

  public Object getBean() {
    return bean;
  }

  public Method getMethod() {
    return method;
  }

  public Class<?> getBeanType() {
    return beanType;
  }

  protected Method getBridgedMethod() {
    return bridgedMethod;
  }

  public MethodParameter[] getMethodParameters() {
    return parameters;
  }

  public MethodParameter getReturnType() {
    return new MethodParameter(bridgedMethod, -1);
  }

  /**
   * Return the actual return value type.
   */
  public MethodParameter getReturnValueType(Object returnValue) {
    return new ReturnValueMethodParameter(returnValue);
  }

  public boolean isVoid() {
    return Void.TYPE.equals(getReturnType().getParameterType());
  }

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof HandlerMethod)) {
      return false;
    } else {
      HandlerMethod otherMethod = (HandlerMethod) other;
      return beanName.equals(otherMethod.beanName) && method.equals(otherMethod.method);
    }
  }

  public int hashCode() {
    return beanName.hashCode() * 31 + method.hashCode();
  }

  protected void assertTargetBean(Method method, Object targetBean, Object[] args) {
    Class<?> methodDeclaringClass = method.getDeclaringClass();
    Class<?> targetBeanClass = targetBean.getClass();
    if (!methodDeclaringClass.isAssignableFrom(targetBeanClass)) {
      String text = "The mapped handler method class '" + methodDeclaringClass.getName()
          + "' is not an instance of the actual controller bean class '" + targetBeanClass.getName()
          + "'. If the controller requires proxying (e.g. due to @Transactional), please use class-based proxying.";
      throw new IllegalStateException(formatInvokeError(text, args));
    }
  }

  protected String formatInvokeError(String text, Object[] args) {
    String formattedArgs = IntStream.range(0, args.length).mapToObj(
        i -> args[i] != null ? "[" + i + "] [type=" + args[i].getClass().getName() + "] [value="
            + args[i] + "]" : "[" + i + "] [null]").collect(Collectors.joining(",\n", " ", " "));
    return text + "\nController [" + getBeanType().getName() + "]\nMethod ["
        + getBridgedMethod().toGenericString() + "] with argument values:\n" + formattedArgs;
  }

  public HandlerMethod createWithResolvedBean() {
    Object handler = this.bean;
    if (handler != null) {
      return this;
    }
    Assert.state(this.beanFactory != null, "Cannot resolve bean name without BeanFactory");
    handler = this.beanFactory.getBean(beanName);
    return new HandlerMethod(this, handler);
  }

  public class ReturnValueMethodParameter extends MethodParameter {

    @Nullable
    private final Class<?> returnValueType;

    public ReturnValueMethodParameter(@Nullable Object returnValue) {
      super(bridgedMethod, -1);
      this.returnValueType = (returnValue != null ? returnValue.getClass() : null);
    }

    protected ReturnValueMethodParameter(ReturnValueMethodParameter original) {
      super(original);
      this.returnValueType = original.returnValueType;
    }

    @Override
    public Class<?> getParameterType() {
      return (returnValueType != null ? returnValueType : super.getParameterType());
    }

    @Override
    public ReturnValueMethodParameter clone() {
      return new ReturnValueMethodParameter(this);
    }
  }
}

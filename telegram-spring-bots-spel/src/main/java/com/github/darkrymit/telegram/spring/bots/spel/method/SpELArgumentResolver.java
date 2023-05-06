package com.github.darkrymit.telegram.spring.bots.spel.method;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.bind.TelegramDataBinderFactory;
import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import com.github.darkrymit.telegram.spring.bots.core.method.argument.HandlerMethodArgumentResolver;
import com.github.darkrymit.telegram.spring.bots.spel.SpELExtract;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.lang.Nullable;

public class SpELArgumentResolver implements HandlerMethodArgumentResolver {

  private final EvaluationContext evaluationContext;

  private final ExpressionParser expressionParser;

  public SpELArgumentResolver(EvaluationContext evaluationContext,
      ExpressionParser expressionParser) {
    this.evaluationContext = evaluationContext;
    this.expressionParser = expressionParser;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return AnnotatedElementUtils.hasAnnotation(parameter.getParameter(), SpELExtract.class);
  }

  @Override
  @Nullable
  public Object resolveArgument(MethodParameter parameter, TelegramRequest request,
      ModelAndViewContainer container, TelegramDataBinderFactory binderFactory) {
    SpELExtract annotation = AnnotatedElementUtils.getMergedAnnotation(parameter.getParameter(), SpELExtract.class);

    if (annotation == null) {
      return null;
    }

    ArgumentResolverRootObject rootObject = new ArgumentResolverRootObject(request);

    Expression expression = expressionParser.parseExpression(annotation.value());

    return expression.getValue(evaluationContext, rootObject,parameter.getParameterType());
  }

}

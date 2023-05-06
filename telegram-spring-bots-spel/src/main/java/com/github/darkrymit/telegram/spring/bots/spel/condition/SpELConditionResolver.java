package com.github.darkrymit.telegram.spring.bots.spel.condition;


import com.github.darkrymit.telegram.spring.bots.core.condition.AbstractAnnotationConditionResolver;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleCondition;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleCondition;
import com.github.darkrymit.telegram.spring.bots.spel.SpEL;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;

public class SpELConditionResolver extends AbstractAnnotationConditionResolver<SpEL> {

  private final EvaluationContext context;

  private final ExpressionParser parser;

  public SpELConditionResolver(EvaluationContext context, ExpressionParser parser) {
    super(SpEL.class);
    this.context = context;
    this.parser = parser;
  }

  @Override
  public List<HandleCondition> resolveHandleConditions(AnnotatedElement element) {
    Collection<SpEL> annotations = extractAnnotations(element);
    List<HandleCondition> conditions = new ArrayList<>();
    if (annotations.isEmpty()) {
      return conditions;
    }
    processConditions(annotations, conditions);
    return conditions;
  }

  @Override
  public List<ExceptionHandleCondition> resolveExceptionHandleConditions(AnnotatedElement element) {
    Collection<SpEL> annotations = extractAnnotations(element);
    List<ExceptionHandleCondition> conditions = new ArrayList<>();
    if (annotations.isEmpty()) {
      return conditions;
    }
    processConditions(annotations, conditions);
    return conditions;
  }

  private void processConditions(Collection<SpEL> annotations,
      List<? super SpELCondition> conditions) {
    for (SpEL annotation : annotations) {
      List<Expression> conditionExpressions = extractExpressions(annotation);
      if (conditionExpressions.isEmpty()) {
        continue;
      }
      conditions.add(new SpELCondition(context, conditionExpressions, annotation.priority()));
    }
  }

  private List<Expression> extractExpressions(SpEL annotation) {
    return Arrays.stream(annotation.value()).filter(s -> !s.isBlank())
        .map(this.parser::parseExpression).collect(Collectors.toList());
  }


}

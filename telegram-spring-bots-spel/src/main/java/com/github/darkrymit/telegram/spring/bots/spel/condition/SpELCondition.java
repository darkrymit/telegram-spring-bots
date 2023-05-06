package com.github.darkrymit.telegram.spring.bots.spel.condition;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleCondition;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleCondition;
import com.github.darkrymit.telegram.spring.bots.spel.TelegramRequestRootObject;
import com.github.darkrymit.telegram.spring.bots.spel.exception.ExceptionHandleConditionRootObject;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

public class SpELCondition implements HandleCondition, ExceptionHandleCondition {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(SpELCondition.class);
  private final EvaluationContext context;

  private final List<Expression> conditionExpressions;

  private final int priority;

  public SpELCondition(EvaluationContext context, List<Expression> conditionExpressions,
      int priority) {
    this.context = context;
    this.conditionExpressions = conditionExpressions;
    this.priority = priority;
  }

  @Override
  public boolean isTrue(TelegramRequest request) {
    try {
      TelegramRequestRootObject rootObject = new TelegramRequestRootObject(request);
      return evaluateExpressions(rootObject);
    } catch (Exception exception) {
      logEvaluateException(exception);
      return false;
    }
  }

  @Override
  public boolean isTrue(TelegramRequest request,@Nullable Object handler, Exception providedException) {
    try {
      ExceptionHandleConditionRootObject rootObject = new ExceptionHandleConditionRootObject(
          request, handler, providedException);
      return evaluateExpressions(rootObject);
    } catch (Exception exception) {
      logEvaluateException(exception);
      return false;
    }
  }

  @Override
  public int getPriority() {
    return priority;
  }

  @Override
  public String toString() {
    return "SpELCondition{" + "context=" + context + ", conditionExpressions="
        + conditionExpressions.stream().map(Expression::getExpressionString)
        .collect(Collectors.toList()) + ", priority=" + priority + '}';
  }

  private boolean evaluateExpressions(Object rootObject) {
    for (Expression exp : conditionExpressions) {
      Boolean value = exp.getValue(this.context, rootObject, Boolean.class);
      if (Boolean.TRUE.equals(value)) {
        return true;
      }
    }
    return false;
  }

  private void logEvaluateException(Exception exception) {
    if (log.isDebugEnabled()) {
      if (log.isTraceEnabled()) {
        log.trace("Exception while evaluating condition: {}", this, exception);
      } else {
        log.debug("Exception [{}]:[{}] while evaluating condition: {} ", exception.getClass(),
            exception.getMessage(), this);
      }
    }
  }
}

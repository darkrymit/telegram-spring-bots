package com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import java.util.Comparator;
import org.springframework.lang.Nullable;

public interface ExceptionHandleCondition {

  ExceptionHandleCondition ALWAYS_TRUE = new ExceptionHandleConditionHelper.AlwaysTrue();

  ExceptionHandleCondition ALWAYS_FALSE = new ExceptionHandleConditionHelper.AlwaysFalse();

  Comparator<ExceptionHandleCondition> COMPARATOR = Comparator.comparingInt(
      ExceptionHandleCondition::getPriority).reversed();

  @Nullable
  static ExceptionHandleCondition and(@Nullable ExceptionHandleCondition conditionA,
      @Nullable ExceptionHandleCondition conditionB) {
    return ExceptionHandleConditionHelper.and(conditionA, conditionB);
  }

  @Nullable
  static ExceptionHandleCondition or(@Nullable ExceptionHandleCondition conditionA,
      @Nullable ExceptionHandleCondition conditionB) {
    return ExceptionHandleConditionHelper.or(conditionA, conditionB);
  }

  @Nullable
  static ExceptionHandleCondition not(@Nullable ExceptionHandleCondition condition) {
    return ExceptionHandleConditionHelper.not(condition);
  }

  boolean isTrue(TelegramRequest request,@Nullable Object handler, Exception exception);

  default int getPriority() {
    return 0;
  }


}

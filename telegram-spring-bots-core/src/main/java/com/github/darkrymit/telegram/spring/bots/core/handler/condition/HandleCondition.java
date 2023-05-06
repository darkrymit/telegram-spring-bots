package com.github.darkrymit.telegram.spring.bots.core.handler.condition;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import java.util.Comparator;
import org.springframework.lang.Nullable;

public interface HandleCondition {

  HandleCondition ALWAYS_TRUE = new HandleConditionHelper.AlwaysTrue();

  HandleCondition ALWAYS_FALSE = new HandleConditionHelper.AlwaysFalse();

  Comparator<HandleCondition> COMPARATOR = Comparator.comparingInt(HandleCondition::getPriority)
      .reversed();

  @Nullable
  static HandleCondition and(@Nullable HandleCondition conditionA,
      @Nullable HandleCondition conditionB) {
    return HandleConditionHelper.and(conditionA, conditionB);
  }

  @Nullable
  static HandleCondition or(@Nullable HandleCondition conditionA,
      @Nullable HandleCondition conditionB) {
    return HandleConditionHelper.or(conditionA, conditionB);
  }

  @Nullable
  static HandleCondition not(@Nullable HandleCondition condition) {
    return HandleConditionHelper.not(condition);
  }

  boolean isTrue(TelegramRequest request);

  default int getPriority() {
    return 0;
  }
}

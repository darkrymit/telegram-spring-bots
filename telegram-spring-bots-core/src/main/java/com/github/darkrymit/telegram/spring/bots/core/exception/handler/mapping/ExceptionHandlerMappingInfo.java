package com.github.darkrymit.telegram.spring.bots.core.exception.handler.mapping;

import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleCondition;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class ExceptionHandlerMappingInfo implements Comparable<ExceptionHandlerMappingInfo> {

  private final ExceptionHandleCondition condition;

  private final int executionPriority;

  @Override
  public int compareTo(@NotNull ExceptionHandlerMappingInfo another) {
    return -Integer.compare(this.executionPriority, another.executionPriority);
  }

  public ExceptionHandlerMappingInfo combine(ExceptionHandlerMappingInfo combined) {
    return new ExceptionHandlerMappingInfo(
        ExceptionHandleCondition.and(this.condition, combined.condition),
        getExecutionPriority(combined.executionPriority));
  }

  private int getExecutionPriority(int otherExecutionOrder) {
    if (executionPriority == 0 && otherExecutionOrder == 0) {
      return 0;
    }
    if (otherExecutionOrder == 0) {
      return executionPriority;
    }
    return otherExecutionOrder;
  }

  @Override
  public String toString() {
    return "HandlerMappingInfo{" +
        "condition=" + condition +
        ", executionPriority=" + executionPriority +
        '}';
  }
}

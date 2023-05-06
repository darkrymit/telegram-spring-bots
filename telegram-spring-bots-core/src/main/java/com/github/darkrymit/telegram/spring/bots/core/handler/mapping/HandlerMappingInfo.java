package com.github.darkrymit.telegram.spring.bots.core.handler.mapping;


import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleCondition;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HandlerMappingInfo implements Comparable<HandlerMappingInfo> {

  private final HandleCondition condition;

  private final int executionPriority;

  public int compareTo(HandlerMappingInfo another) {
    return -Integer.compare(this.executionPriority, another.executionPriority);
  }

  public HandlerMappingInfo combine(HandlerMappingInfo combined) {
    return new HandlerMappingInfo(HandleCondition.and(this.condition, combined.condition),
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HandlerMappingInfo)) {
      return false;
    }
    HandlerMappingInfo that = (HandlerMappingInfo) o;
    return executionPriority == that.executionPriority && Objects.equals(condition,
        that.condition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(condition, executionPriority);
  }
}
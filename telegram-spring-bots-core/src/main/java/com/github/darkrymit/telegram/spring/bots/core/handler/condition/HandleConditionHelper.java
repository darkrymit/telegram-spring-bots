package com.github.darkrymit.telegram.spring.bots.core.handler.condition;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import org.springframework.lang.Nullable;

public class HandleConditionHelper {

  private HandleConditionHelper() {
  }

  @Nullable
  static HandleCondition and(@Nullable HandleCondition conditionA,
      @Nullable HandleCondition conditionB) {

    if (conditionA == null && conditionB == null) {
      return null;
    }

    if (conditionA == null) {
      return conditionB;
    }

    if (conditionB == null) {
      return conditionA;
    }

    return new And(conditionA, conditionB);
  }

  @Nullable
  static HandleCondition or(@Nullable HandleCondition conditionA,
      @Nullable HandleCondition conditionB) {

    if (conditionA == null && conditionB == null) {
      return null;
    }

    if (conditionA == null) {
      return conditionB;
    }

    if (conditionB == null) {
      return conditionA;
    }

    return new Or(conditionA, conditionB);
  }

  @Nullable
  static HandleCondition not(@Nullable HandleCondition condition) {
    if (condition == null) {
      return null;
    }
    return new Not(condition);
  }

  public static class AlwaysTrue implements HandleCondition {

    @Override
    public boolean isTrue(TelegramRequest request) {
      return true;
    }

    @Override
    public String toString() {
      return "AlwaysTrue{}";
    }
  }

  public static class AlwaysFalse implements HandleCondition {

    @Override
    public boolean isTrue(TelegramRequest request) {
      return false;
    }

    @Override
    public String toString() {
      return "AlwaysFalse{}";
    }
  }

  public static class And implements HandleCondition {

    private final HandleCondition conditionA;
    private final HandleCondition conditionB;

    public And(HandleCondition conditionA, HandleCondition conditionB) {
      this.conditionA = conditionA;
      this.conditionB = conditionB;
    }

    @Override
    public boolean isTrue(TelegramRequest request) {
      return conditionA.isTrue(request) && conditionB.isTrue(request);
    }

    @Override
    public String toString() {
      return "And{" +
          "conditionA=" + conditionA +
          ", conditionB=" + conditionB +
          '}';
    }
  }

  public static class Or implements HandleCondition {

    private final HandleCondition conditionA;
    private final HandleCondition conditionB;

    public Or(HandleCondition conditionA, HandleCondition conditionB) {
      this.conditionA = conditionA;
      this.conditionB = conditionB;
    }

    @Override
    public boolean isTrue(TelegramRequest request) {
      return conditionA.isTrue(request) || conditionB.isTrue(request);
    }

    @Override
    public String toString() {
      return "Or{" +
          "conditionA=" + conditionA +
          ", conditionB=" + conditionB +
          '}';
    }
  }

  public static class Not implements HandleCondition {

    private final HandleCondition condition;

    public Not(HandleCondition condition) {
      this.condition = condition;
    }

    @Override
    public boolean isTrue(TelegramRequest request) {
      return !condition.isTrue(request);
    }

    @Override
    public String toString() {
      return "Not{" +
          "condition=" + condition +
          '}';
    }
  }
}

package com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import org.springframework.lang.Nullable;

public class ExceptionHandleConditionHelper {

  private ExceptionHandleConditionHelper() {
  }

  @Nullable
  static ExceptionHandleCondition and(@Nullable ExceptionHandleCondition conditionA,
      @Nullable ExceptionHandleCondition conditionB) {

    if (conditionA == null && conditionB == null) {
      return null;
    }

    if (conditionA == null) {
      return conditionB;
    }

    if (conditionB == null) {
      return conditionA;
    }

    return new ExceptionHandleConditionHelper.And(conditionA, conditionB);
  }

  @Nullable
  static ExceptionHandleCondition or(@Nullable ExceptionHandleCondition conditionA,
      @Nullable ExceptionHandleCondition conditionB) {

    if (conditionA == null && conditionB == null) {
      return null;
    }

    if (conditionA == null) {
      return conditionB;
    }

    if (conditionB == null) {
      return conditionA;
    }

    return new ExceptionHandleConditionHelper.Or(conditionA, conditionB);
  }

  @Nullable
  static ExceptionHandleCondition not(@Nullable ExceptionHandleCondition condition) {
    if (condition == null) {
      return null;
    }
    return new ExceptionHandleConditionHelper.Not(condition);
  }

  public static class AlwaysTrue implements ExceptionHandleCondition {

    @Override
    public boolean isTrue(TelegramRequest request, Object handler, Exception exception) {
      return true;
    }

    @Override
    public String toString() {
      return "AlwaysTrue{}";
    }
  }

  public static class AlwaysFalse implements ExceptionHandleCondition {

    @Override
    public boolean isTrue(TelegramRequest request, Object handler, Exception exception) {
      return false;
    }

    @Override
    public String toString() {
      return "AlwaysFalse{}";
    }
  }

  public static class And implements ExceptionHandleCondition {

    private final ExceptionHandleCondition conditionA;
    private final ExceptionHandleCondition conditionB;

    public And(ExceptionHandleCondition conditionA, ExceptionHandleCondition conditionB) {
      this.conditionA = conditionA;
      this.conditionB = conditionB;
    }

    @Override
    public boolean isTrue(TelegramRequest request, Object handler, Exception exception) {
      return conditionA.isTrue(request, handler, exception) && conditionB.isTrue(request, handler,
          exception);
    }

    @Override
    public String toString() {
      return "And{" + "conditionA=" + conditionA + ", conditionB=" + conditionB + '}';
    }
  }

  public static class Or implements ExceptionHandleCondition {

    private final ExceptionHandleCondition conditionA;
    private final ExceptionHandleCondition conditionB;

    public Or(ExceptionHandleCondition conditionA, ExceptionHandleCondition conditionB) {
      this.conditionA = conditionA;
      this.conditionB = conditionB;
    }

    @Override
    public boolean isTrue(TelegramRequest request, Object handler, Exception exception) {
      return conditionA.isTrue(request, handler, exception) || conditionB.isTrue(request, handler,
          exception);
    }

    @Override
    public String toString() {
      return "Or{" + "conditionA=" + conditionA + ", conditionB=" + conditionB + '}';
    }
  }

  public static class Not implements ExceptionHandleCondition {

    private final ExceptionHandleCondition condition;

    public Not(ExceptionHandleCondition condition) {
      this.condition = condition;
    }

    @Override
    public boolean isTrue(TelegramRequest request, Object handler, Exception exception) {
      return !condition.isTrue(request, handler, exception);
    }

    @Override
    public String toString() {
      return "Not{" + "condition=" + condition + '}';
    }
  }
}

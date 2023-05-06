package com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition;

import java.lang.reflect.AnnotatedElement;
import java.util.List;
import org.springframework.lang.Nullable;

/**
 * Interface for a component that resolves {@link ExceptionHandleCondition} objects for exception
 * handlers.
 *
 * <p>Update data condition resolvers are used to retrieve a {@link ExceptionHandleCondition}
 * object for a given {@link AnnotatedElement}, which can be used to determine if an exception
 * should be processed by an exception handler.
 */
public interface ExceptionHandleConditionResolver {

  /**
   * Determine if this resolver supports the given {@link AnnotatedElement}.
   *
   * @param element the element to check for support
   * @return {@code true} if this resolver supports the given {@link AnnotatedElement}, or
   * {@code false} otherwise
   */
  boolean supports(AnnotatedElement element);

  /**
   * Resolve the {@link ExceptionHandleCondition} for the given {@link AnnotatedElement}.
   *
   * @param element the element for which to resolve the {@link ExceptionHandleCondition}
   * @return resolved {@link List} of  {@link ExceptionHandleCondition} for the given
   * {@link AnnotatedElement}, or empty {@link  List} if not resolvable
   */

  default List<ExceptionHandleCondition> resolveExceptionHandleConditions(
      AnnotatedElement element) {
    ExceptionHandleCondition condition = resolveExceptionHandleCondition(element);
    if (condition == null) {
      return List.of();
    }
    return List.of(condition);
  }

  /**
   * Resolve the {@link ExceptionHandleCondition} for the given {@link AnnotatedElement}.
   *
   * @param element the element for which to resolve the {@link ExceptionHandleCondition}
   * @return resolved {@link ExceptionHandleCondition} for the given {@link AnnotatedElement}, or
   * {@code null} if not resolvable
   */
  @Nullable
  default ExceptionHandleCondition resolveExceptionHandleCondition(AnnotatedElement element) {
    return null;
  }
}

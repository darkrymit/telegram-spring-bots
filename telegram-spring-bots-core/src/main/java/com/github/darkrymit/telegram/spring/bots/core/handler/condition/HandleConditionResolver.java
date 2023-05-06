package com.github.darkrymit.telegram.spring.bots.core.handler.condition;

import java.lang.reflect.AnnotatedElement;
import java.util.List;
import org.springframework.lang.Nullable;

/**
 * Interface for a component that resolves {@link HandleCondition} objects for a Telegram bot.
 *
 * <p>Update data condition resolvers are used to retrieve a {@link HandleCondition}
 * object for a given {@link AnnotatedElement}, which can be used to determine if an update should
 * be processed by a bot.
 */
public interface HandleConditionResolver {

  /**
   * Determine if this resolver supports the given {@link AnnotatedElement}.
   *
   * @param element the element to check for support
   * @return {@code true} if this resolver supports the given {@link AnnotatedElement}, or
   * {@code false} otherwise
   */
  boolean supports(AnnotatedElement element);

  /**
   * Resolve all the {@link HandleCondition} for the given {@link AnnotatedElement}.
   *
   * <p>Override only if you need to resolve multiple {@link HandleCondition} for an element.
   *
   * @param element the element for which to resolve the {@link HandleCondition}
   * @return resolved {@link List} of {@link HandleCondition} for the given
   * {@link AnnotatedElement}, or empty {@link  List} if not resolvable
   */
  default List<HandleCondition> resolveHandleConditions(AnnotatedElement element) {
    HandleCondition condition = resolveHandleCondition(element);
    if (condition == null) {
      return List.of();
    }
    return List.of(condition);
  }

  /**
   * Resolve the {@link HandleCondition} for the given {@link AnnotatedElement}.
   *
   * <p>Override only if you need to resolve a single {@link HandleCondition} for an element.
   *
   * @param element the element for which to resolve the {@link HandleCondition}
   * @return resolved {@link HandleCondition} for the given {@link AnnotatedElement}, or
   * {@code null} if not resolvable
   */
  @Nullable
  default HandleCondition resolveHandleCondition(AnnotatedElement element){
    return null;
  }
}

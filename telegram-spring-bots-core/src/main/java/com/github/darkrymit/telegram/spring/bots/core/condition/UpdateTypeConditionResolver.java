package com.github.darkrymit.telegram.spring.bots.core.condition;

import com.github.darkrymit.telegram.spring.bots.core.annotation.IsUpdateType;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleCondition;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleCondition;
import java.lang.reflect.AnnotatedElement;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

public class UpdateTypeConditionResolver extends AbstractAnnotationConditionResolver<IsUpdateType> {

  public UpdateTypeConditionResolver() {
    super(IsUpdateType.class);
  }

  @Override
  public ExceptionHandleCondition resolveExceptionHandleCondition(AnnotatedElement element) {
    return getUpdateTypeCondition(element);
  }

  @Override
  public HandleCondition resolveHandleCondition(AnnotatedElement element) {
    return getUpdateTypeCondition(element);
  }

  @Nullable
  private UpdateTypeCondition getUpdateTypeCondition(AnnotatedElement element) {
    IsUpdateType annotation = extractAnnotation(element);
    if (annotation == null) {
      return null;
    }
    return new UpdateTypeCondition(Set.of(annotation.value()), annotation.priority());
  }

}

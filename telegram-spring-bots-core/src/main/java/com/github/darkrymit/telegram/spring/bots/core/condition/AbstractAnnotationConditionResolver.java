package com.github.darkrymit.telegram.spring.bots.core.condition;

import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleConditionResolver;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleConditionResolver;
import com.github.darkrymit.telegram.spring.bots.core.support.AdditionalAnnotatedElementUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.annotation.AnnotatedElementUtils;

public class AbstractAnnotationConditionResolver<T extends Annotation> implements
    HandleConditionResolver, ExceptionHandleConditionResolver {

  private final Class<T> annotationType;
  private final boolean repeatable;

  public AbstractAnnotationConditionResolver(Class<T> annotationType) {
    this.annotationType = annotationType;
    this.repeatable = isRepeatable();
  }

  @Override
  public boolean supports(AnnotatedElement element) {
    if (repeatable) {
      return AdditionalAnnotatedElementUtils.hasRepeatableAnnotation(element, annotationType);
    }
    return AnnotatedElementUtils.hasAnnotation(element, annotationType);
  }

  protected boolean isRepeatable() {
    Repeatable annotation = annotationType.getAnnotation(Repeatable.class);
    return annotation != null;
  }

  protected Collection<T> extractAnnotations(AnnotatedElement element) {
    return AnnotatedElementUtils.findMergedRepeatableAnnotations(element, annotationType);
  }

  @Nullable
  protected T extractAnnotation(AnnotatedElement element) {
    return AnnotatedElementUtils.findMergedAnnotation(element, annotationType);
  }
}

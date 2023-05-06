package com.github.darkrymit.telegram.spring.bots.core.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.core.annotation.RepeatableContainers;

public abstract class AdditionalAnnotatedElementUtils {

  public static boolean hasRepeatableAnnotation(AnnotatedElement element,
      Class<? extends Annotation> annotationType) {
    RepeatableContainers.of(annotationType, null);
    return MergedAnnotations.from(element, SearchStrategy.TYPE_HIERARCHY,
        RepeatableContainers.standardRepeatables()).isPresent(annotationType);
  }

}

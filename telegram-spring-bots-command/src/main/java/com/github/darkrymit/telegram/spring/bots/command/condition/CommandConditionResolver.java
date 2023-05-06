package com.github.darkrymit.telegram.spring.bots.command.condition;


import com.github.darkrymit.telegram.spring.bots.command.IsCommand;
import com.github.darkrymit.telegram.spring.bots.command.support.CommandUtils;
import com.github.darkrymit.telegram.spring.bots.core.condition.AbstractAnnotationConditionResolver;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleCondition;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleCondition;
import java.lang.reflect.AnnotatedElement;
import org.jetbrains.annotations.Nullable;

public class CommandConditionResolver extends AbstractAnnotationConditionResolver<IsCommand> {

  public CommandConditionResolver() {
    super(IsCommand.class);
  }

  @Nullable
  private CommandCondition getCommandCondition(AnnotatedElement element) {
    IsCommand annotation = extractAnnotation(element);
    if (annotation == null) {
      return null;
    }
    return new CommandCondition(CommandUtils.filterCommands(annotation.value()),
        annotation.requireMentionInGroup(), annotation.priority());
  }

  @Override
  @Nullable
  public ExceptionHandleCondition resolveExceptionHandleCondition(AnnotatedElement element) {
    return getCommandCondition(element);
  }

  @Override
  @Nullable
  public HandleCondition resolveHandleCondition(AnnotatedElement element) {
    return getCommandCondition(element);
  }

}

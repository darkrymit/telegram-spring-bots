package com.github.darkrymit.telegram.spring.bots.core.condition;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.UpdateType;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.condition.ExceptionHandleCondition;
import com.github.darkrymit.telegram.spring.bots.core.handler.condition.HandleCondition;
import java.util.Set;
import org.springframework.lang.Nullable;

public class UpdateTypeCondition implements HandleCondition, ExceptionHandleCondition {

  private final Set<UpdateType> updateTypes;

  private final int priority;


  public UpdateTypeCondition(Set<UpdateType> updateTypes, int priority) {
    this.updateTypes = updateTypes;
    this.priority = priority;
  }

  @Override
  public boolean isTrue(TelegramRequest request,@Nullable Object handler, Exception exception) {
    return updateTypes.contains(request.getUpdateType());
  }

  @Override
  public boolean isTrue(TelegramRequest request) {
    return updateTypes.contains(request.getUpdateType());
  }

  @Override
  public int getPriority() {
    return priority;
  }
}

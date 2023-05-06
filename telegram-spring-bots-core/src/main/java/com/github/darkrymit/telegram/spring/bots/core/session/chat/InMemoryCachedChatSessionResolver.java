package com.github.darkrymit.telegram.spring.bots.core.session.chat;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class InMemoryCachedChatSessionResolver implements ChatSessionResolver {

  private final Cache<Long, ChatSession> sessions;

  public InMemoryCachedChatSessionResolver(long expireMilleSeconds) {
    sessions = CacheBuilder
        .newBuilder()
        .expireAfterAccess(expireMilleSeconds, TimeUnit.MILLISECONDS)
        .build();
  }

  @Override
  public ChatSession getOrCreate(Long userId) {
    try {
      return sessions.get(userId, () -> new InMemoryChatSession(userId));
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}

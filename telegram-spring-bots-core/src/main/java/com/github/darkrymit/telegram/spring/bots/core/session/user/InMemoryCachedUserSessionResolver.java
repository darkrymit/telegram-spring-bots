package com.github.darkrymit.telegram.spring.bots.core.session.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class InMemoryCachedUserSessionResolver implements UserSessionResolver {

  private final Cache<Long, UserSession> sessions;

  public InMemoryCachedUserSessionResolver(long expireMilleSeconds) {
    sessions = CacheBuilder
        .newBuilder()
        .expireAfterAccess(expireMilleSeconds, TimeUnit.MILLISECONDS)
        .build();
  }

  @Override
  public UserSession getOrCreate(Long userId) {
    try {
      return sessions.get(userId, () -> new InMemoryUserSession(userId));
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}

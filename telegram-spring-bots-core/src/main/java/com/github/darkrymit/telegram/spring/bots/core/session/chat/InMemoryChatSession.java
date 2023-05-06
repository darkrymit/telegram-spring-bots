package com.github.darkrymit.telegram.spring.bots.core.session.chat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryChatSession implements ChatSession {

  private final Map<String, Object> map = new ConcurrentHashMap<>();

  private final Long id;

  public InMemoryChatSession(Long sessionId) {
    this.id = sessionId;
  }

  @Override
  public Map<String, Object> getMap() {
    return map;
  }

  @Override
  public Object getAttribute(String attribute) {
    return map.get(attribute);
  }

  @Override
  public <T> T getAttribute(String attribute, Class<T> target) {
    return target.cast(map.get(attribute));
  }

  @Override
  public boolean hasAttribute(String attribute) {
    return map.containsKey(attribute);
  }

  @Override
  public void setAttribute(String attribute, Object value) {
    map.put(attribute, value);
  }

  @Override
  public void removeAttribute(String attribute) {
    map.remove(attribute);
  }

  @Override
  public Long getSessionId() {
    return id;
  }
}

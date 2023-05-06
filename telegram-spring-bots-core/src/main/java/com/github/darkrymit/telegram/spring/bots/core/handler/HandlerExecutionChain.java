package com.github.darkrymit.telegram.spring.bots.core.handler;


import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import com.github.darkrymit.telegram.spring.bots.core.handler.interceptor.HandlerInterceptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

@Slf4j
public class HandlerExecutionChain {

  private final Object handler;
  private final List<HandlerInterceptor> interceptorList;
  private int interceptorIndex;

  public HandlerExecutionChain(Object handler) {
    this(handler, (HandlerInterceptor[]) null);
  }

  public HandlerExecutionChain(Object handler, @Nullable HandlerInterceptor... interceptors) {
    this(handler, interceptors != null ? Arrays.asList(interceptors) : Collections.emptyList());
  }

  public HandlerExecutionChain(Object handler, List<HandlerInterceptor> interceptorList) {
    this.interceptorList = new ArrayList<>();
    this.interceptorIndex = -1;
    if (handler instanceof HandlerExecutionChain) {
      HandlerExecutionChain originalChain = (HandlerExecutionChain) handler;
      this.handler = originalChain.getHandler();
      this.interceptorList.addAll(originalChain.interceptorList);
    } else {
      this.handler = handler;
    }

    this.interceptorList.addAll(interceptorList);
  }

  public Object getHandler() {
    return this.handler;
  }

  public void addInterceptor(HandlerInterceptor interceptor) {
    this.interceptorList.add(interceptor);
  }

  public void addInterceptor(int index, HandlerInterceptor interceptor) {
    this.interceptorList.add(index, interceptor);
  }

  public void addInterceptors(List<HandlerInterceptor> interceptors) {
    interceptorList.addAll(interceptors);
  }

  @Nullable
  public HandlerInterceptor[] getInterceptors() {
    return !this.interceptorList.isEmpty() ? this.interceptorList.toArray(new HandlerInterceptor[0])
        : null;
  }

  public List<HandlerInterceptor> getInterceptorList() {
    return !this.interceptorList.isEmpty() ? Collections.unmodifiableList(this.interceptorList)
        : Collections.emptyList();
  }

  public boolean applyPreHandle(TelegramRequest request) throws Exception {
    for (int i = 0; i < this.interceptorList.size(); this.interceptorIndex = i++) {
      HandlerInterceptor interceptor = this.interceptorList.get(i);
      if (!interceptor.preHandle(request, this.handler)) {
        this.triggerAfterCompletion(request, null);
        return false;
      }
    }

    return true;
  }

  public void applyPostHandle(TelegramRequest request) throws Exception {
    for (int i = this.interceptorList.size() - 1; i >= 0; --i) {
      HandlerInterceptor interceptor = this.interceptorList.get(i);
      interceptor.postHandle(request, this.handler);
    }

  }

  public void triggerAfterCompletion(TelegramRequest request, @Nullable Exception ex) {
    for (int i = this.interceptorIndex; i >= 0; --i) {
      HandlerInterceptor interceptor = this.interceptorList.get(i);

      try {
        interceptor.afterCompletion(request, this.handler, ex);
      } catch (Throwable throwable) {
        log.error("HandlerInterceptor.afterCompletion threw exception", throwable);
      }
    }

  }

  public String toString() {
    return "HandlerExecutionChain with [" + this.getHandler() + "] and "
        + this.interceptorList.size() + " interceptors";
  }
}
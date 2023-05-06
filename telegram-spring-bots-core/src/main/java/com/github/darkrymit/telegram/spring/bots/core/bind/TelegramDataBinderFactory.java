package com.github.darkrymit.telegram.spring.bots.core.bind;

import com.github.darkrymit.telegram.spring.bots.core.TelegramRequest;
import org.springframework.validation.DataBinder;

public interface TelegramDataBinderFactory {

  DataBinder createBinder(TelegramRequest request, Object target, String name) throws Exception;
}

package com.github.darkrymit.telegram.spring.bots.core.support;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.ui.ModelMap;

public interface View {

  View EMPTY = (model, bot) -> {};

  void render(ModelMap model, TelegramBot bot) throws Exception;
}

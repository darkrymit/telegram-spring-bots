package com.github.darkrymit.telegram.spring.bots.core;

import com.pengrad.telegrambot.TelegramBot;
import java.util.Objects;

public final class BotRegistration {

  private final BotMetaInfo metaInfo;
  private final TelegramBot bot;

  public BotRegistration(BotMetaInfo metaInfo, TelegramBot bot) {
    this.metaInfo = metaInfo;
    this.bot = bot;
  }

  public BotMetaInfo getMetaInfo() {
    return this.metaInfo;
  }

  public TelegramBot getBot() {
    return this.bot;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BotRegistration)) {
      return false;
    }
    BotRegistration that = (BotRegistration) o;
    return Objects.equals(metaInfo, that.metaInfo) && Objects.equals(bot,
        that.bot);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metaInfo, bot);
  }

  public String toString() {
    return "BotRegistration(metaInfo=" + this.getMetaInfo() + ", bot=" + this.getBot() + ")";
  }
}

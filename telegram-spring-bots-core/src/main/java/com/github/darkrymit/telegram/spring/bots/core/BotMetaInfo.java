package com.github.darkrymit.telegram.spring.bots.core;

import java.util.Objects;

public final class BotMetaInfo {

  private final String internalName;
  private final String nickName;

  public BotMetaInfo(String internalName, String nickName) {
    this.internalName = internalName;
    this.nickName = nickName;
  }

  public String getInternalName() {
    return this.internalName;
  }

  public String getNickName() {
    return this.nickName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BotMetaInfo)) {
      return false;
    }
    BotMetaInfo that = (BotMetaInfo) o;
    return Objects.equals(internalName, that.internalName) && Objects.equals(
        nickName, that.nickName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(internalName, nickName);
  }

  public String toString() {
    return "BotMetaInfo(internalName=" + this.getInternalName() + ", nickName=" + this.getNickName()
        + ")";
  }
}

package com.github.darkrymit.telegram.spring.bots.core.support;

import com.github.darkrymit.telegram.spring.bots.core.method.ModelAndViewContainer;
import org.jetbrains.annotations.Nullable;

public class ModelAndViewUtils {

  private ModelAndViewUtils() {
  }

  @Nullable
  public static ModelAndView getModelAndView(ModelAndViewContainer mavContainer) {
    if (mavContainer.isRequestHandled()) {
      return null;
    }

    ModelAndView mav;

    if (mavContainer.isViewReference()) {
      mav = new ModelAndView(mavContainer.getViewName(), mavContainer.getModel());
    } else {
      mav = new ModelAndView(mavContainer.getView(), mavContainer.getModel());
    }

    return mav;
  }
}

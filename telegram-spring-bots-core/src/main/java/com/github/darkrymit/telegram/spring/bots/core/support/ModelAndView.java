package com.github.darkrymit.telegram.spring.bots.core.support;

import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;

public class ModelAndView {

  private Object viewValue;

  private ModelMap model;

  public ModelAndView(String viewName, ModelMap model) {
    this.viewValue = viewName;
    this.model = model;
  }

  public ModelAndView(View view, ModelMap model) {
    this.viewValue = view;
    this.model = model;
  }

  @Nullable
  public View getView() {
    return viewValue instanceof View ? (View) viewValue : null;
  }

  public void setView(View view) {
    this.viewValue = view;
  }

  @Nullable
  public String getViewName() {
    return viewValue instanceof String ? (String) viewValue : null;
  }

  public void setViewName(String viewName) {
    this.viewValue = viewName;
  }

  public boolean isViewReference() {
    return viewValue instanceof String;
  }

  public ModelMap getModel() {
    return model;
  }

  public void setModel(ModelMap model) {
    this.model = model;
  }
}

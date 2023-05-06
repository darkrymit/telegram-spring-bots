package com.github.darkrymit.telegram.spring.bots.core.method;

import com.github.darkrymit.telegram.spring.bots.core.support.View;
import org.springframework.lang.Nullable;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;
import org.springframework.validation.support.BindingAwareModelMap;

public class ModelAndViewContainer {

  private final ExtendedModelMap model = new BindingAwareModelMap();

  @Nullable
  private Object viewValue = null;

  private boolean requestHandled = false;

  @Nullable
  public String getViewName() {
    return viewValue instanceof String ? (String) viewValue : null;
  }

  public void setViewName(String viewName) {
    this.viewValue = viewName;
  }

  @Nullable
  public View getView() {
    return viewValue instanceof View ? (View) viewValue : null;
  }

  public void setView(View view) {
    this.viewValue = view;
  }

  public boolean isViewReference() {
    return viewValue instanceof String;
  }

  public ExtendedModelMap getModel() {
    return model;
  }

  public boolean isRequestHandled() {
    return requestHandled;
  }

  public void setRequestHandled(boolean requestHandled) {
    this.requestHandled = requestHandled;
  }

  public ModelAndViewContainer addAttribute(String name, Object value) {
    this.model.addAttribute(name, value);
    return this;
  }

  public ModelAndViewContainer addAllAttributes(ModelMap attributes) {
    this.model.addAllAttributes(attributes);
    return this;
  }

  public boolean containsAttribute(String name) {
    return this.model.containsAttribute(name);
  }

  @Override
  public String toString() {
    return "ModelAndViewContainer{" +
        "model=" + model +
        ", view=" + viewValue +
        ", requestHandled=" + requestHandled +
        '}';
  }
}

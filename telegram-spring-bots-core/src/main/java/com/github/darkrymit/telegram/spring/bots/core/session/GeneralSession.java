package com.github.darkrymit.telegram.spring.bots.core.session;

import java.util.Map;
import org.springframework.lang.Nullable;

public interface GeneralSession<I> {

  /**
   * Returns a map containing all the attributes stored in this session.
   *
   * @return a map of attribute names to attribute values
   */
  Map<String, Object> getMap();

  /**
   * Returns the value of the specified attribute in this session.
   *
   * @param attribute the name of the attribute to retrieve
   * @return the value of the specified attribute, or {@code null} if the attribute is not set
   */
  @Nullable
  Object getAttribute(String attribute);

  /**
   * Returns the value of the specified attribute in this session, cast to the specified target
   * type.
   *
   * @param attribute the name of the attribute to retrieve
   * @param target    the target class to cast the attribute value to
   * @param <T>       the target type
   * @return the value of the specified attribute, cast to the target type, or {@code null} if the
   * attribute is not set
   * @throws ClassCastException if the attribute value cannot be cast to the target type
   */
  @Nullable
  <T> T getAttribute(String attribute, Class<T> target);


  /**
   * Returns {@code true} if the session contains an attribute with the specified name. Otherwise,
   * returns {@code false}.
   *
   * @param attribute the name of the attribute to check
   * @return {@code true} if the session contains an attribute with the specified name, otherwise
   * {@code false}
   */
  boolean hasAttribute(String attribute);

  /**
   * Sets the value of the specified attribute in this session.
   *
   * @param attribute the name of the attribute to set
   * @param value     the value to set the attribute to
   */
  void setAttribute(String attribute, Object value);

  /**
   * Removes the specified attribute from this session.
   *
   * @param attribute the name of the attribute to remove
   */
  void removeAttribute(String attribute);

  /**
   * Returns the unique identifier for this session.
   *
   * @return the session ID
   */
  I getSessionId();
}

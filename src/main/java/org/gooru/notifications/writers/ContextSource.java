package org.gooru.notifications.writers;

import java.util.HashMap;
import java.util.Map;

public enum ContextSource {
  CourseMap("course-map"),
  ClassActivity("class-activity"),
  Proficiency("proficiency");

  private final String name;

  ContextSource(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  private static final Map<String, ContextSource> LOOKUP = new HashMap<>(values().length);

  static {
    for (ContextSource suggestionArea : values()) {
      LOOKUP.put(suggestionArea.name, suggestionArea);
    }
  }
  
  public static boolean isValid(String value) {
    return (value != null) && (LOOKUP.containsKey(value));
  }
  
  public static String findContextSource(NotificationsEvent event) {
    if (event.getContentSource() != null) {
      return event.getContentSource();
    }
    return CourseMap.name;
  }
}

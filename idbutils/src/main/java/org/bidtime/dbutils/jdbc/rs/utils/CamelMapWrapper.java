package org.bidtime.dbutils.jdbc.rs.utils;

public class CamelMapWrapper {

  public String findProperty(String name, boolean useCamelCaseMapping) {
    if (useCamelCaseMapping
        && ((name.charAt(0) >= 'A' && name.charAt(0) <= 'Z') || name.indexOf("_") >= 0)) {
      return underlineToCamelhump(name);
    }
    return name;
  }

  /**
   * 将下划线风格替换为驼峰风格
   */
  public String underlineToCamelhump(String inputString) {
    StringBuilder sb = new StringBuilder();
    boolean nextUpperCase = false;
    for (int i = 0; i < inputString.length(); i++) {
      char c = inputString.charAt(i);
      if (c == '_') {
        if (sb.length() > 0) {
          nextUpperCase = true;
        }
      } else {
        if (nextUpperCase) {
          sb.append(Character.toUpperCase(c));
          nextUpperCase = false;
        } else {
          sb.append(Character.toLowerCase(c));
        }
      }
    }
    return sb.toString();
  }
  
}

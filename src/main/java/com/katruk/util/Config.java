package com.katruk.util;

import java.util.ResourceBundle;

public final class Config {

  private static Config instance = new Config();
  private static final String BUNDLE_NAME = "config";
  private ResourceBundle configFile;

  static final String DRIVER = "driver";
  static final String URL = "url";

  public static final String INDEX = "index";
  public static final String REGISTRATION = "registration";
  public static final String PROFILE = "profile";


  public static final String ADMIN_PROFILE = "ADMIN_PROFILE";
  public static final String ALL_HUMANS = "ALL_HUMANS";
  public static final String ALL_STUDENTS = "ALL_STUDENTS";
  public static final String ALL_TEACHERS = "ALL_TEACHERS";
  public static final String TEACHER_PROFILE = "TEACHER_PROFILE";
  public static final String TEACHER_DISCIPLINES = "TEACHER_DISCIPLINES";
  public static final String TEACHER_EVALUATION = "TEACHER_EVALUATION";
  public static final String TEACHER_CONFIRMED = "TEACHER_CONFIRMED";
  public static final String STUDENT_PROFILE = "STUDENT_PROFILE";
  public static final String STUDENT_MARKS = "STUDENT_MARKS";
  public static final String STUDENT_DECLARED_DISCIPLINES = "STUDENT_DECLARED_DISCIPLINES";
  public static final String DISCIPLINES = "DISCIPLINES";
  public static final String ERROR_PAGE = "error.page";

  private Config() {
    configFile = ResourceBundle.getBundle(BUNDLE_NAME);
  }


  public static Config getInstance() {
    return instance;
  }

  public String getValue(String key) {
    return configFile.getString(key);
  }

}

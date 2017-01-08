package com.katruk.util;

import java.util.ResourceBundle;

public final class Config {

  private ResourceBundle configFile;
  private static final String BUNDLE_NAME = "config";
  private static final Config INSTANCE = new Config();

  public static final String DRIVER = "driver";
  public static final String URL = "url";
  public static final String INDEX = "index";
  public static final String REGISTRATION = "registration";
  public static final String PROFILE = "profile";
  public static final String ADMIN_PROFILE = "admin.profile";
  public static final String ALL_USERS = "users";
  public static final String ALL_STUDENTS = "students";
  public static final String ALL_TEACHERS = "teachers";
  public static final String ALL_SUBJECTS = "subjects";
  public static final String ADD_SUBJECT = "addSubject";


  public static final String TEACHER_PROFILE = "TEACHER_PROFILE";
  public static final String TEACHER_DISCIPLINES = "TEACHER_DISCIPLINES";
  public static final String TEACHER_EVALUATION = "TEACHER_EVALUATION";
  public static final String TEACHER_CONFIRMED = "TEACHER_CONFIRMED";
  public static final String STUDENT_PROFILE = "STUDENT_PROFILE";
  public static final String STUDENT_MARKS = "STUDENT_MARKS";
  public static final String STUDENT_DECLARED_DISCIPLINES = "STUDENT_DECLARED_DISCIPLINES";

  public static final String ERROR_PAGE = "error.page";

  private Config() {
    configFile = ResourceBundle.getBundle(BUNDLE_NAME);
  }

  public static Config getInstance() {
    return INSTANCE;
  }

  public String getValue(String key) {
    return configFile.getString(key);
  }

}

package com.katruk.util;

import java.util.ResourceBundle;

public final class PageConfig {

  private ResourceBundle configFile;
  private static final String BUNDLE_NAME = "page";
  private static final PageConfig INSTANCE = new PageConfig();

  public static final String INDEX = "index";
  public static final String REGISTRATION = "registration";
  public static final String PROFILE = "profile";
  public static final String ADMIN_PROFILE = "admin.profile";
  public static final String USERS = "admin.users";
  public static final String STUDENTS = "admin.students";
  public static final String TEACHERS = "admin.teachers";
  public static final String SUBJECTS = "subjects";
  public static final String ADD_SUBJECT = "admin.addSubject";

  public static final String TEACHER_SUBJECTS = "teacher.subjects";
//  public static final String TEACHER_PROFILE = "TEACHER_PROFILE";

  public static final String TEACHER_EVALUATIONS = "teacher.evaluations";
  public static final String EVALUATION = "teacher.evaluation";

  public static final String TEACHER_CONFIRMED = "TEACHER_CONFIRMED";

//  public static final String STUDENT_PROFILE = "STUDENT_PROFILE";
  public static final String STUDENT_EVALUATIONS = "student.evaluations";
//  public static final String STUDENT_DECLARED_DISCIPLINES = "STUDENT_DECLARED_DISCIPLINES";

  public static final String ERROR_PAGE = "error.page";

  private PageConfig() {
    configFile = ResourceBundle.getBundle(BUNDLE_NAME);
  }

  public static PageConfig getInstance() {
    return INSTANCE;
  }

  public String getValue(String key) {
    return configFile.getString(key);
  }
}

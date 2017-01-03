package com.katruk.util;

import java.util.ResourceBundle;

public final class Sql {

  private static final String BUNDLE_NAME_SQL = "sql";
  private ResourceBundle configFile;

  private static final Sql INSTANCE = new Sql();

  public static final String CREATE_PERSON = "CREATE_PERSON";
  public static final String GET_PERSON_BY_ID = "GET_PERSON_BY_ID";

  public static final String CREATE_USER = "CREATE_USER";
  public static final String GET_USER_BY_USERNAME = "GET_USER_BY_USERNAME";
  public static final String GET_USER_BY_ID = "GET_USER_BY_ID";

  public static final String CREATE_STUDENT = "CREATE_STUDENT";
  public static final String GET_STUDENT_BY_ID = "GET_STUDENT_BY_ID";

  public static final String CREATE_TEACHER = "CREATE_TEACHER";
  public static final String GET_TEACHER_BY_ID = "GET_TEACHER_BY_ID";

  public static final String CREATE_SUBJECT = "CREATE_SUBJECT";
  public static final String GET_SUBJECT_BY_ID = "GET_SUBJECT_BY_ID";
  public static final String GET_SUBJECT_BY_TEACHER = "GET_SUBJECT_BY_TEACHER";

  public static final String CREATE_EVALUATION = "CREATE_EVALUATION";
  public static final String GET_EVALUATION_BY_ID = "GET_EVALUATION_BY_ID";
  public static final String GET_EVALUATION_BY_STUDENT = "GET_EVALUATION_BY_STUDENT";
  public static final String GET_EVALUATION_BY_SUBJECT = "GET_EVALUATION_BY_SUBJECT";

  private Sql() {
    configFile = ResourceBundle.getBundle(BUNDLE_NAME_SQL);
  }

  public static Sql getInstance() {
    return INSTANCE;
  }

  public String get(String key) {
    return configFile.getString(key);
  }

}
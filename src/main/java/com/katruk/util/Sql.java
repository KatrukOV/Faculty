package com.katruk.util;

import java.util.ResourceBundle;

public final class Sql {

  private static final String BUNDLE_NAME_SQL = "sql";
  private ResourceBundle configFile;
  private static final Sql INSTANCE = new Sql();
  /*  PERIOD  */
  public static final String GET_LAST_PERIOD = "GET_LAST_PERIOD";
  public static final String INSERT_PERIOD = "INSERT_PERIOD";
  /*  PERSON  */
  public static final String CREATE_PERSON = "CREATE_PERSON";
  public static final String UPDATE_PERSON = "UPDATE_PERSON";
  public static final String GET_PERSON_BY_ID = "GET_PERSON_BY_ID";
  public static final String GET_ALL_PERSON = "GET_ALL_PERSON";
  /*  USER  */
  public static final String REPLACE_USER = "REPLACE_USER";
  public static final String GET_USER_BY_USERNAME = "GET_USER_BY_USERNAME";
  public static final String GET_USER_BY_ID = "GET_USER_BY_ID";
  public static final String GET_ALL_USER = "GET_ALL_USER";
  /*  STUDENT  */
  public static final String REPLACE_STUDENT = "REPLACE_STUDENT";
  public static final String DELETE_STUDENT = "DELETE_STUDENT";
  public static final String GET_STUDENT_BY_ID = "GET_STUDENT_BY_ID";
  public static final String GET_ALL_STUDENT = "GET_ALL_STUDENT";
  /*  TEACHER  */
  public static final String REPLACE_TEACHER = "REPLACE_TEACHER";
  public static final String DELETE_TEACHER = "DELETE_TEACHER";
  public static final String GET_TEACHER_BY_ID = "GET_TEACHER_BY_ID";
  public static final String GET_ALL_TEACHER = "GET_ALL_TEACHER";
  /*  SUBJECT  */
  public static final String CREATE_SUBJECT = "CREATE_SUBJECT";
  public static final String UPDATE_SUBJECT = "UPDATE_SUBJECT";
  public static final String GET_ALL_SUBJECT = "GET_ALL_SUBJECT";
  public static final String GET_SUBJECT_BY_ID = "GET_SUBJECT_BY_ID";
  public static final String GET_SUBJECT_BY_TEACHER = "GET_SUBJECT_BY_TEACHER";
  public static final String DELETE_SUBJECT = "DELETE_SUBJECT";
  /*  EVALUATION  */
  public static final String CREATE_EVALUATION = "CREATE_EVALUATION";
  public static final String UPDATE_EVALUATION = "UPDATE_EVALUATION";
  public static final String GET_EVALUATION_BY_SUBJECT_AND_STUDENT =
      "GET_EVALUATION_BY_SUBJECT_AND_STUDENT";
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

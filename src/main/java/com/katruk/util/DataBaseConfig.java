package com.katruk.util;

import java.util.ResourceBundle;

final class DataBaseConfig {

  static final String DRIVER = "driver";
  static final String URL = "url";
  static final String USERNAME = "username";
  static final String PASSWORD = "password";
  private static final DataBaseConfig INSTANCE = new DataBaseConfig();
  private static final String BUNDLE_NAME = "database";
  private ResourceBundle configFile;

  private DataBaseConfig() {
    configFile = ResourceBundle.getBundle(BUNDLE_NAME);
  }

  public static DataBaseConfig getInstance() {
    return INSTANCE;
  }

  String getValue(String key) {
    return configFile.getString(key);
  }
}

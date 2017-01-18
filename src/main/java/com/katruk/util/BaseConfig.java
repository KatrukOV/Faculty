package com.katruk.util;

import java.util.ResourceBundle;

final class BaseConfig {

  private ResourceBundle configFile;
  private static final BaseConfig INSTANCE = new BaseConfig();
  private static final String BUNDLE_NAME = "database";
  static final String DRIVER = "driver";
  static final String URL = "url";
  static final String USERNAME = "username";
  static final String PASSWORD = "password";

  private BaseConfig() {
    configFile = ResourceBundle.getBundle(BUNDLE_NAME);
  }

  public static BaseConfig getInstance() {
    return INSTANCE;
  }

  String getValue(String key) {
    return configFile.getString(key);
  }
}

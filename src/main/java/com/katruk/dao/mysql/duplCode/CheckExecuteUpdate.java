package com.katruk.dao.mysql.duplCode;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CheckExecuteUpdate {

  private final PreparedStatement statement;
  private final String reason;

  public CheckExecuteUpdate(PreparedStatement statement, String reason) {
    this.statement = statement;
    this.reason = reason;
  }

  public void check() throws SQLException {
    int affectedRows = statement.executeUpdate();
    if (affectedRows == 0) {
      throw new SQLException(reason);
    }
  }
}

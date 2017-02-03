package com.katruk.dao.mysql.duplCode;

import static java.util.Objects.nonNull;

import com.katruk.dao.mysql.DataBaseNames;
import com.katruk.entity.Student;
import com.katruk.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetStudent implements DataBaseNames {

  private final ResultSet resultSet;

  public GetStudent(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  public Student get() throws SQLException {
    Student student = new Student();
    User user = new GetUser(resultSet).get();
    student.setUser(user);
    student.setId(user.getId());
    String form = resultSet.getString(FORM);
    if (nonNull(form)) {
      student.setForm(Student.Form.valueOf(form));
    }
    String contract = resultSet.getString(CONTRACT);
    if (nonNull(contract)) {
      student.setContract(Student.Contract.valueOf(contract));
    }
    return student;
  }
}

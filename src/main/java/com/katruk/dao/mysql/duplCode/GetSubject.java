package com.katruk.dao.mysql.duplCode;

import static java.util.Objects.nonNull;

import com.katruk.dao.mysql.DataBaseNames;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetSubject implements DataBaseNames {

  private final ResultSet resultSet;

  public GetSubject(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  public Subject get() throws SQLException {
    Subject subject = new Subject();
    Teacher teacher = new Teacher();
    User user = new GetUser(resultSet).get();
    teacher.setId(user.getId());
    teacher.setUser(user);
    String position = resultSet.getString(POSITION);
    if (nonNull(position)) {
      teacher.setPosition(Teacher.Position.valueOf(position));
    }
    subject.setTeacher(teacher);
    subject.setTitle(resultSet.getString(TITLE));
    subject.setId(resultSet.getLong(ID));
    return subject;
  }
}

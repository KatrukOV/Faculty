package com.katruk.dao.mysql.duplCode;

import static com.katruk.web.PageAttribute.LAST_NAME;
import static java.util.Objects.nonNull;

import com.katruk.dao.mysql.DataBaseNames;
import com.katruk.entity.Person;
import com.katruk.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUser implements DataBaseNames {

  private final ResultSet resultSet;

  public GetUser(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  public User get() throws SQLException {
    User user = new User();
    Person person = new Person();
    person.setId(resultSet.getLong(PERSON_ID));
    person.setLastName(resultSet.getString(LAST_NAME));
    person.setName(resultSet.getString(NAME));
    person.setPatronymic(resultSet.getString(PATRONYMIC));
    user.setId(person.getId());
    user.setPerson(person);
    user.setUsername(resultSet.getString(USERNAME));
    user.setPassword(resultSet.getString(PASSWORD));
    if (nonNull(resultSet.getString(ROLE))) {
      user.setRole(User.Role.valueOf(resultSet.getString(ROLE)));
    }
    return user;
  }
}

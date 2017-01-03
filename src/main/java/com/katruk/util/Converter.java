package com.katruk.util;

import com.katruk.entity.Person;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;

import org.apache.commons.codec.digest.DigestUtils;

public final class Converter {

  public User convertDto(UserDto userDto) {
    User user = new User();
    Person person = new Person();
    person.setLastName(userDto.getLastName());
    person.setName(userDto.getName());
    person.setPatronymic(userDto.getPatronymic());
    user.setPerson(person);
    user.setUsername(userDto.getUsername());
    user.setPassword(encodePassword(userDto.getPassword()));
    return user;
  }

  public UserDto convertUser(User user) {
    UserDto userDto = new UserDto();
    userDto.setLastName(user.getPerson().getLastName());
    userDto.setName(user.getPerson().getName());
    userDto.setPatronymic(user.getPerson().getPatronymic());
    userDto.setUsername(user.getUsername());
    userDto.setPassword(user.getPassword());
    return userDto;
  }

  //Sha1Hex encryption method
  private String encodePassword(String password) {
    return DigestUtils.sha1Hex(password);
  }


}

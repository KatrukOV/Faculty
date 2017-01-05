package com.katruk.converter;

import static java.util.stream.Collectors.toList;

import com.katruk.entity.Person;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public final class UserConverter {

  private final Function<User, UserDto> toDto = this::convertToDto;


  public List<UserDto> convertToDto(Collection<User> users) {
    return users.stream()
        .map(toDto)
        .collect(toList());
  }

  public User convertToUser(UserDto userDto) {
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

  public UserDto convertToDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setUserId(user.getId());
    userDto.setLastName(user.getPerson().getLastName());
    userDto.setName(user.getPerson().getName());
    userDto.setPatronymic(user.getPerson().getPatronymic());
    userDto.setUsername(user.getUsername());
//    userDto.setPassword(user.getPassword());
    userDto.setRole(user.getRole());
    return userDto;
  }

  //Sha1Hex encryption method
  private String encodePassword(String password) {
    return DigestUtils.sha1Hex(password);
  }
}

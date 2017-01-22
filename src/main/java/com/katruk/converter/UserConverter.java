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
    Person person = new Person(userDto.getLastName(), userDto.getName(), userDto.getPatronymic());
    String username = userDto.getUsername();
    String password = encodePassword(userDto.getPassword());
    return new User(person, username, password);
  }

  public UserDto convertToDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setUserId(user.id());
    userDto.setLastName(user.person().lastName());
    userDto.setName(user.person().name());
    userDto.setPatronymic(user.person().patronymic());
    userDto.setUsername(user.username());
    userDto.setRole(user.role());
    return userDto;
  }

  //Sha1Hex encryption method
  private String encodePassword(String password) {
    return DigestUtils.sha1Hex(password);
  }
}

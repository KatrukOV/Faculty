package com.katruk.converter;

import static java.util.stream.Collectors.toList;

import com.katruk.entity.Person;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.entity.dto.TeacherDto;
import com.katruk.entity.dto.UserDto;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public final class TeacherConverter {

  private final Function<Teacher, TeacherDto> toDto = this::convertToDto;

  public List<TeacherDto> convertToDto(Collection<Teacher> teachers) {
    return teachers.stream()
        .map(toDto)
        .collect(toList());
  }

  public TeacherDto convertToDto(Teacher teacher) {
    TeacherDto teacherDto = new TeacherDto();
    teacherDto.setLastName(teacher.getUser().getPerson().getLastName());
    teacherDto.setName(teacher.getUser().getPerson().getName());
    teacherDto.setPatronymic(teacher.getUser().getPerson().getPatronymic());
    teacherDto.setPosition(teacher.getPosition());
    return teacherDto;
  }
}

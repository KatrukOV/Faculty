package com.katruk.converter;

import static java.util.stream.Collectors.toList;

import com.katruk.entity.Teacher;
import com.katruk.entity.dto.TeacherDto;

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
    teacherDto.setTeacherId(teacher.id());
    teacherDto.setLastName(teacher.user().person().lastName());
    teacherDto.setName(teacher.user().person().name());
    teacherDto.setPatronymic(teacher.user().person().patronymic());
    teacherDto.setPosition(teacher.position());
    return teacherDto;
  }
}

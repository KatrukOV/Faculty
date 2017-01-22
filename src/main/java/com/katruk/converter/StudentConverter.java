package com.katruk.converter;

import static java.util.stream.Collectors.toList;

import com.katruk.entity.Student;
import com.katruk.entity.dto.StudentDto;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public final class StudentConverter {

  private final Function<Student, StudentDto> toDto = this::convertToDto;

  public List<StudentDto> convertToDto(Collection<Student> students) {
    return students.stream()
        .map(toDto)
        .collect(toList());
  }

  public StudentDto convertToDto(Student student) {
    StudentDto studentDto = new StudentDto();
    studentDto.setStudentId(student.id());
    studentDto.setLastName(student.user().person().lastName());
    studentDto.setName(student.user().person().name());
    studentDto.setPatronymic(student.user().person().patronymic());
    studentDto.setForm(student.form());
    studentDto.setContract(student.contract());
    return studentDto;
  }
}

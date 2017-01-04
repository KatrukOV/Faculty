package com.katruk.converter;

import static java.util.stream.Collectors.toList;

import com.katruk.entity.Student;
import com.katruk.entity.Teacher;
import com.katruk.entity.dto.StudentDto;
import com.katruk.entity.dto.TeacherDto;

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
    studentDto.setLastName(student.getUser().getPerson().getLastName());
    studentDto.setName(student.getUser().getPerson().getName());
    studentDto.setPatronymic(student.getUser().getPerson().getPatronymic());
    studentDto.setForm(student.getForm());
    studentDto.setContract(student.getContract());
    return studentDto;
  }
}

package com.katruk.converter;

import static java.util.stream.Collectors.toList;

import com.katruk.entity.Subject;
import com.katruk.entity.dto.SubjectDto;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public final class SubjectConverter {

  private final Function<Subject, SubjectDto> toDto = this::convertToDto;

  public List<SubjectDto> convertToDto(Collection<Subject> subjects) {
    return subjects.stream()
        .map(toDto)
        .collect(toList());
  }

  public SubjectDto convertToDto(Subject subject) {
    SubjectDto subjectDto = new SubjectDto();
    subjectDto.setSubjectId(subject.getId());
    subjectDto.setLastName(subject.getTeacher().getUser().getPerson().getLastName());
    subjectDto.setName(subject.getTeacher().getUser().getPerson().getName());
    subjectDto.setPatronymic(subject.getTeacher().getUser().getPerson().getPatronymic());
    subjectDto.setPosition(subject.getTeacher().getPosition());
    subjectDto.setTitle(subject.getTitle());
    return subjectDto;
  }
}

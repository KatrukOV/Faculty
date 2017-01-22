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
    subjectDto.setSubjectId(subject.id());
    subjectDto.setLastName(subject.teacher().user().person().lastName());
    subjectDto.setName(subject.teacher().user().person().name());
    subjectDto.setPatronymic(subject.teacher().user().person().patronymic());
    subjectDto.setPosition(subject.teacher().position());
    subjectDto.setTitle(subject.title());
    return subjectDto;
  }
}

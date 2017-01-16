package com.katruk.converter;

import static java.util.stream.Collectors.toList;

import com.katruk.entity.Evaluation;
import com.katruk.entity.Subject;
import com.katruk.entity.dto.EvaluationDto;
import com.katruk.entity.dto.SubjectDto;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public final class EvaluationConverter {

  private final Function<Evaluation, EvaluationDto> toDto = this::convertToDto;

  public List<EvaluationDto> convertToDto(Collection<Evaluation> evaluations) {
    return evaluations.stream()
        .map(toDto)
        .collect(toList());
  }

  public EvaluationDto convertToDto(Evaluation evaluation) {
    EvaluationDto evaluationDto = new EvaluationDto();
    evaluationDto.setTitle(evaluation.getSubject().getTitle());
    evaluationDto.setLastName(evaluation.getStudent().getUser().getPerson().getLastName());
    evaluationDto.setName(evaluation.getStudent().getUser().getPerson().getName());
    evaluationDto.setPatronymic(evaluation.getStudent().getUser().getPerson().getPatronymic());
    evaluationDto.setRating(evaluation.getRating());
    evaluationDto.setFeedback(evaluation.getFeedback());
    return evaluationDto;
  }
}

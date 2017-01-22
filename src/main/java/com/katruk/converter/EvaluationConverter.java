package com.katruk.converter;

import static java.util.stream.Collectors.toList;

import com.katruk.entity.Evaluation;
import com.katruk.entity.dto.EvaluationDto;

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
    evaluationDto.setEvaluationId(evaluation.id());
    evaluationDto.setTitle(evaluation.subject().title());
    evaluationDto.setLastName(evaluation.student().user().person().lastName());
    evaluationDto.setName(evaluation.student().user().person().name());
    evaluationDto.setPatronymic(evaluation.student().user().person().patronymic());
    evaluationDto.setStatus(evaluation.status());
    evaluationDto.setRating(evaluation.rating());
    evaluationDto.setFeedback(evaluation.feedback());
    return evaluationDto;
  }
}

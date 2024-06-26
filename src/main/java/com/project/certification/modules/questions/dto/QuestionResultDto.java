package com.project.certification.modules.questions.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResultDto {

    private UUID id;
    private String technology;
    private String description;

    private List<AlternativesResultDto> alternatives;
}
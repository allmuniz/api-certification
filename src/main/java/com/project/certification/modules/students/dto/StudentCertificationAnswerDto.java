package com.project.certification.modules.students.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCertificationAnswerDto {

    private String email;
    private String technology;
    private List<QuestionAnswerDto> questionsAnswers;
}

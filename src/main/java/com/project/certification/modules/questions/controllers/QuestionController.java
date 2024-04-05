package com.project.certification.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.certification.modules.questions.dto.AlternativesResultDto;
import com.project.certification.modules.questions.dto.QuestionResultDto;
import com.project.certification.modules.questions.entities.AlternativesEntity;
import com.project.certification.modules.questions.entities.QuestionEntity;
import com.project.certification.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    
    @GetMapping("/technology/{technology}")
    public List<QuestionResultDto> findByTechnology(@PathVariable String technology){
        
        var result = this.questionRepository.findByTechnology(technology);
        var toMap = result.stream().map(question -> mapQuestionToDto(question)).collect(Collectors.toList());
        return toMap;
    }

    static QuestionResultDto mapQuestionToDto(QuestionEntity question){

        var questionResultDto = QuestionResultDto.builder()
                                                 .id(question.getId())
                                                 .technology(question.getTechnology())
                                                 .description(question.getDescription())
                                                 .build();

        List<AlternativesResultDto> alternativesResultDto = question.getAlternatives().stream()
                                                                    .map(alternative -> mapAlternativeDto(alternative))
                                                                    .collect(Collectors.toList());
        
        questionResultDto.setAlternatives(alternativesResultDto);
        return questionResultDto;
    }

    static AlternativesResultDto mapAlternativeDto(AlternativesEntity alternativesEntity){

        return AlternativesResultDto.builder()
                                    .id(alternativesEntity.getId())
                                    .description(alternativesEntity.getDescription())
                                    .build();
    }
}

package com.project.certification.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.certification.modules.questions.entities.QuestionEntity;
import com.project.certification.modules.questions.repositories.QuestionRepository;
import com.project.certification.modules.students.dto.StudentCertificationAnswerDto;
import com.project.certification.modules.students.entities.AnswersCertificationEntity;
import com.project.certification.modules.students.entities.CertificationStudentEntity;
import com.project.certification.modules.students.entities.StudentEntity;
import com.project.certification.modules.students.repositories.CertificationStudentRepository;
import com.project.certification.modules.students.repositories.StudentRepository;

import lombok.var;

@Service
public class StudentCertificationAnswersUseCase {
    
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    
    public CertificationStudentEntity execute(StudentCertificationAnswerDto dto) throws Exception{

        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());

        dto.getQuestionsAnswers().stream().forEach(questionAnswer ->{

            var question = questionsEntity.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionId()))
                                                    .findFirst().get();

            var findCorrectAlternative = question.getAlternatives().stream().filter(alternative -> alternative.getIsCorrect())
                                                 .findFirst().get();

            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeId())) {
                
                questionAnswer.setCorrect(true);
            }else{
                questionAnswer.setCorrect(false);
            }
        });

        //Verifica se existe um estudante pelo email
        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentId;
        if (student.isEmpty()) {

            var newStudent = StudentEntity.builder()
                                          .email(dto.getEmail())
                                          .build();

            studentRepository.save(newStudent);
            studentId = newStudent.getId();
        }else{

            studentId = student.get().getId();
        }

        List<AnswersCertificationEntity> answersCertifications = new ArrayList<>();

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                                                                                          .studentId(studentId)
                                                                                          .technology(dto.getTechnology())
                                                                                          .answersCertificationEntities(answersCertifications)
                                                                                          .build();
        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);
        
        return certificationStudentCreated;
    }
}

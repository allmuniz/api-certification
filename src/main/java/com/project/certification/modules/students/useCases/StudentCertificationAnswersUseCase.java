package com.project.certification.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.certification.modules.questions.entities.QuestionEntity;
import com.project.certification.modules.questions.repositories.QuestionRepository;
import com.project.certification.modules.students.dto.StudentCertificationAnswerDto;
import com.project.certification.modules.students.dto.StudentVerifyIfHasCertificationDto;
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

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;
    
    public CertificationStudentEntity execute(StudentCertificationAnswerDto dto) throws Exception{

        var hasCertifycation = this.verifyIfHasCertificationUseCase.execute(new StudentVerifyIfHasCertificationDto(dto.getEmail(), dto.getTechnology()));

        if (hasCertifycation) {
            throw new Exception("Você ja tirou sua cerificação");
        }

        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationEntity> answersCertifications = new ArrayList<>();

        AtomicInteger corretcAnswers = new AtomicInteger(0);
        
        dto.getQuestionsAnswers().stream().forEach(questionAnswer ->{

            var question = questionsEntity.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionId()))
                                                    .findFirst().get();

            var findCorrectAlternative = question.getAlternatives().stream().filter(alternative -> alternative.getIsCorrect())
                                                 .findFirst().get();

            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeId())) {
                
                questionAnswer.setCorrect(true);
                corretcAnswers.incrementAndGet();
            }else{
                questionAnswer.setCorrect(false);
            }

            var answersCertificationEntity = AnswersCertificationEntity.builder()
                                                                       .answerId(questionAnswer.getAlternativeId())
                                                                       .questionId(questionAnswer.getQuestionId())
                                                                       .isCorrect(questionAnswer.isCorrect())
                                                                       .build();
            answersCertifications.add(answersCertificationEntity);
        });

        //Aqui

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


        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                                                                                          .studentId(studentId)
                                                                                          .technology(dto.getTechnology())
                                                                                          .grade(corretcAnswers.get())
                                                                                          .build();
        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answersCertification -> {

            answersCertification.setCertificationId(certificationStudentEntity.getId());
            answersCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationEntities(answersCertifications);
        
        certificationStudentRepository.save(certificationStudentEntity);
        
        return certificationStudentCreated;
    }
}

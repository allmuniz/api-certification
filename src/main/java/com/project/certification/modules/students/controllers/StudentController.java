package com.project.certification.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.certification.modules.students.dto.StudentCertificationAnswerDto;
import com.project.certification.modules.students.dto.StudentVerifyIfHasCertificationDto;
import com.project.certification.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.project.certification.modules.students.useCases.VerifyIfHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody StudentVerifyIfHasCertificationDto studentVerifyIfHasCertificationDto){
        
        var result = this.verifyIfHasCertificationUseCase.execute(studentVerifyIfHasCertificationDto);
        if (result) {
            return "Usuário já fez a prova";
        }
        return "Usuário pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswer(@RequestBody StudentCertificationAnswerDto dto) throws Exception{

        try {
            var result = this.studentCertificationAnswersUseCase.execute(dto);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

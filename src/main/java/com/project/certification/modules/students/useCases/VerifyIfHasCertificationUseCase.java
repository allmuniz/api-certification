package com.project.certification.modules.students.useCases;

import org.springframework.stereotype.Service;

import com.project.certification.modules.students.dto.StudentVerifyIfHasCertificationDto;

@Service
public class VerifyIfHasCertificationUseCase {

    public Boolean execute(StudentVerifyIfHasCertificationDto dto){
        
        if (dto.getEmail().equals("teste1@teste.teste") && dto.getTechnology().equals("TESTE1")) {
            
            return true;
        }
        return false;
    }
}

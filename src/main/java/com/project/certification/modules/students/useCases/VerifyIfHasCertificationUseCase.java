package com.project.certification.modules.students.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.certification.modules.students.dto.StudentVerifyIfHasCertificationDto;
import com.project.certification.modules.students.repositories.CertificationStudentRepository;

@Service
public class VerifyIfHasCertificationUseCase {

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public Boolean execute(StudentVerifyIfHasCertificationDto dto){
        
        var result = this.certificationStudentRepository.findByStudentEmailAndTechnology(dto.getEmail(), dto.getTechnology());

        if (!result.isEmpty()) {
            
            return true;
        }
        return false;
    }
}

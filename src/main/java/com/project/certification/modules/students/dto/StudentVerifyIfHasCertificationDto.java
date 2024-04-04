package com.project.certification.modules.students.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentVerifyIfHasCertificationDto{

    private String email;
    private String technology;
}

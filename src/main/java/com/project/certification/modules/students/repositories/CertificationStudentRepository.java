package com.project.certification.modules.students.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.certification.modules.students.entities.CertificationStudentEntity;

@Repository
public interface CertificationStudentRepository extends JpaRepository<CertificationStudentEntity, UUID>{

    @Query("SELECT c FROM tb_certificatios c INNER JOIN c.studentEntity std WHERE std.email = :email AND c.technology = technology")
    List<CertificationStudentEntity> findByStudentEmailAndTechnology(String email, String technology);
}
package com.project.certification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Estudos",
		description = "API responsavel pela geração de perguntas",
		version = "1"
	)
)
public class CertificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificationApplication.class, args);
	}

}

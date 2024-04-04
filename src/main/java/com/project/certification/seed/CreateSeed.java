package com.project.certification.seed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CreateSeed {

    private final JdbcTemplate jdbcTemplate;

    public CreateSeed(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static void main(String[] args) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5434/api_certification");
        dataSource.setUsername("admin1");
        dataSource.setPassword("admin1");

        CreateSeed createSeed = new CreateSeed(dataSource);
        createSeed.run(args);
    }

    public void run(String... args) {
        executeSqlFile("src/main/resources/create.sql");
    }

    private void executeSqlFile(String filePath) {

        try {
            Path path = Paths.get(filePath);
            String sqlScript = new String(Files.readAllBytes(path));

            jdbcTemplate.execute(sqlScript);
            System.out.println("Seed realizado com sucesso");
        } catch (IOException e) {
            System.err.println("Erro ao executar arquivo" + e.getMessage());
        }
    }
}

package com.buzas.lesson7homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("com.buzas.lesson7homework.entities.students")
@EnableJpaRepositories("com.buzas.lesson7homework.entities.students")
@EnableTransactionManagement
public class Lesson7HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lesson7HomeworkApplication.class, args);
    }

}

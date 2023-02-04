package com.challenge.rbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.challenge.rbc.repository")
@EntityScan(basePackages = "com.challenge.rbc.entity")
public class RbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbcApplication.class, args);
    }

}

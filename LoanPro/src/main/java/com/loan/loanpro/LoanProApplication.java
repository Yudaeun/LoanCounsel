package com.loan.loanpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LoanProApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanProApplication.class, args);
    }

}

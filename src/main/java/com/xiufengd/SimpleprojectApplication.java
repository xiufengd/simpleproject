package com.xiufengd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimpleprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleprojectApplication.class, args);
    }

}
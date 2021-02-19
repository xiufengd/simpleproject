package com.xiufengd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.xiufengd.mapper")
public class SimpleprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleprojectApplication.class, args);
    }

}

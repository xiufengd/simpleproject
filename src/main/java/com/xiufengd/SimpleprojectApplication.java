package com.xiufengd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.xiufengd.mapper")
@ServletComponentScan("com.xiufengd.base.filter")
public class SimpleprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleprojectApplication.class, args);
    }

}

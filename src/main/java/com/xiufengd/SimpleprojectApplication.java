package com.xiufengd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling //开启定时器
@MapperScan("com.xiufengd.mapper") //扫描mapper
@ServletComponentScan("com.xiufengd.base.filter") //扫描过滤器
@EnableCaching //启动缓存
public class SimpleprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleprojectApplication.class, args);
    }

}

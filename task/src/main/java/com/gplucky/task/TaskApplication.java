package com.gplucky.task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by ehsy_it on 2017/1/25.
 */
@SpringBootApplication
@MapperScan("com.gplucky.common.mybatis.dao")
@ImportResource({"classpath:/common.xml"})
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

}

package com.gplucky.task;

import com.mongodb.MongoClientOptions;
import feign.Request;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by ehsy_it on 2017/1/25.
 */
@SpringBootApplication
@MapperScan("com.gplucky.common.mybatis.dao")
@ImportResource({"classpath:/common.xml"})
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients
@EnableMongoRepositories("com.gplucky.task.service.mongo")
public class TaskApplication {

    @Value("${gbj.feign.timeout}")
    private int timeout;

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions.builder().socketTimeout(60000)
                .connectTimeout(60000)
                .maxWaitTime(60000)
                .build();
    }

    @Bean
    Request.Options feignOptions() {
        return new Request.Options(timeout, timeout);
    }
}

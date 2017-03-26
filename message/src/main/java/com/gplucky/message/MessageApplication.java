package com.gplucky.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by ehsy_it on 2017/1/25.
 */
@SpringBootApplication
@ImportResource({"classpath:/common.xml"})
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients
public class MessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }

}

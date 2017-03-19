package com.gplucky.parameter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableMongoRepositories
@EnableFeignClients
@EnableZuulProxy
@EnableCircuitBreaker
public class ParameterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParameterApplication.class, args);
	}
}

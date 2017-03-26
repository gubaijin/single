package com.gplucky.parameter;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableMongoRepositories
@EnableFeignClients
@EnableZuulProxy
@EnableCircuitBreaker
@Configuration
public class ParameterApplication {

	@Value("${gbj.feign.timeout}")
	private int timeout;

	public static void main(String[] args) {
		SpringApplication.run(ParameterApplication.class, args);
	}

	@Bean
	Request.Options feignOptions() {
		return new Request.Options(timeout, timeout);
	}
}
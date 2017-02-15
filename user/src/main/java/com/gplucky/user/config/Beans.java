package com.gplucky.user.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ehsy_it on 2017/2/13.
 */
@Configuration
public class Beans {

    @Bean
    public FilterRegistrationBean indexFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new SimpleCORSFilter());
        registration.addUrlPatterns("/");
        return registration;
    }
}

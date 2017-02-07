package com.gplucky.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ehsy_it on 2017/1/25.
 */
@SpringBootApplication
@RestController
public class UiApplication {

    @RequestMapping(value = "/search")
    public Person search(String personName){
        Person person = new Person();
        person.setName(personName);
        person.setAddress("1111");
        person.setAge(30);
        return person;
    }

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }

}

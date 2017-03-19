package com.gplucky.parameter.service;

import com.gplucky.parameter.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ehsy_it on 2017/3/19.
 */
public interface PersonRepository extends MongoRepository<Person, String> {
    Person findByName(String name);

}

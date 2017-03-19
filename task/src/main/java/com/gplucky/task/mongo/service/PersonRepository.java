package com.gplucky.task.mongo.service;

import com.gplucky.task.mongo.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ehsy_it on 2017/3/19.
 */
public interface PersonRepository extends MongoRepository {

    Person findByName(String name);

}

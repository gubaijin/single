package com.gplucky.parameter.service;

import com.gplucky.parameter.model.RSI;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ehsy_it on 2017/3/19.
 */
public interface RSIRepository extends MongoRepository<RSI, ObjectId> {

}

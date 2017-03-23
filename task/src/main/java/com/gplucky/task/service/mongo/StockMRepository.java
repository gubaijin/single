package com.gplucky.task.service.mongo;

import com.gplucky.common.bean.mongo.StockM;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ehsy_it on 2017/3/20.
 */
public interface StockMRepository extends MongoRepository<StockM, ObjectId> {
}

package com.gplucky.common.bean.mongo;

import com.gplucky.common.mybatis.model.Stock;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by ehsy_it on 2017/3/20.
 */
@Document
public class StockM extends Stock {
    @Id
    private String id;
}

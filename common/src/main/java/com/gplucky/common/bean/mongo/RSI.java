package com.gplucky.common.bean.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by ehsy_it on 2017/3/19
 */
@Document
public class RSI {

    @Id
    private String id;
    private String code;
    private LinkedList<Map<String, Double>> RSIList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LinkedList<Map<String, Double>> getRSIList() {
        return RSIList;
    }

    public void setRSIList(LinkedList<Map<String, Double>> RSIList) {
        this.RSIList = RSIList;
    }
}

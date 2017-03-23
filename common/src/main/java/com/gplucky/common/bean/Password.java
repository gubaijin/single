package com.gplucky.common.bean;

/**
 * Created by ehsy_it on 2017/3/22.
 */
public enum Password {
    PWD("gbj");

    private String value;

    private Password(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

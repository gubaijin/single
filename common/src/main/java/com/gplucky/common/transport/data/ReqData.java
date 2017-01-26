package com.gplucky.common.transport.data;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class ReqData <T> {
    private String contentType;
    private T content;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

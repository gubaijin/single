package com.gplucky.common.transport;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class RequestBuilder {
    //连接超时时间，默认10秒
    private int socketTimeout;
    //传输超时时间，默认30秒
    private int connectTimeout;
    //请求器的配置
    private RequestConfig requestConfig;
    //HTTP请求器
    private CloseableHttpClient httpClient;

    public RequestBuilder setSocketTimeout(int socketTimeout){
        this.socketTimeout = socketTimeout;
        return this;
    }

    public RequestBuilder setConnectTimeout(int connectTimeout){
        this.connectTimeout = connectTimeout;
        return this;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static RequestBuilder create(){
        return new RequestBuilder();
    }

    public HttpRequest build(){
        httpClient = HttpClients.createDefault();
        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        return new DefaultHttpRequest(httpClient, requestConfig);
    }
}

package com.gplucky.common.transport;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class RequestUtil {

    public static HttpRequest createDefault(){
        return RequestBuilder.create().build();
    }

    public static HttpRequest createDefault(int socketTimeout, int connectTimeout){
        return RequestBuilder.create().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }
}

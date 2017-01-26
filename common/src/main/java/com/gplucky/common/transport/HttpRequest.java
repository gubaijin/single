package com.gplucky.common.transport;

import com.gplucky.common.transport.data.RespData;

import java.util.Map;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public interface HttpRequest {
    /**
     * GET方式调用服务
     * @param svcURI 服务地址
     * @param params 参数
     * @param resClass 返回数据类型
     * @param <T>
     * @return 响应数据
     */
    public <T> RespData<T> get(String svcURI, Map<String, Object> params, Class<T> resClass);

    /**
     * POST方式调用服务,报文内容为String
     * @param svcURI 服务地址
     * @param contentType 请求报文类型
     * @param content 请求报文
     * @param resClass 响应报文类型
     * @param <T>
     * @return 响应报文
     */
    public <T> RespData<T> post(String svcURI, String contentType, String content, Class<T> resClass);

    /**
     * POST方式调用服务
     * @param svcURI 服务地址
     * @param params 请求参数
     * @param resClass 响应报文类型
     * @param <T>
     * @return 响应报文
     */
    public <T> RespData<T> post(String svcURI, String contentType, Map<String, Object> params, Class<T> resClass);

    /**
     * PUT方式调用服务
     * @param svcURI 服务地址
     * @param params 请求参数
     * @param resClass 响应报文类型
     * @param <T>
     * @return 响应报文
     */
    public <T> RespData<T> put(String svcURI, Map<String, Object> params, Class<T> resClass);
}

package com.gplucky.common.transport;

import com.alibaba.fastjson.JSONObject;
import com.gplucky.common.transport.data.RespData;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class DefaultHttpRequest implements HttpRequest {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultHttpRequest.class);

    private static final String CONNECTOR_1 = "?";
    private static final String CONNECTOR_2 = "&";

    //请求器的配置
    private RequestConfig requestConfig;
    //HTTP请求器
    private CloseableHttpClient httpClient;

    public DefaultHttpRequest(CloseableHttpClient httpClient, RequestConfig requestConfig) {
        this.httpClient = httpClient;
        this.requestConfig = requestConfig;
    }

    @Override
    public <T> RespData<T> get(String svcURI, Map<String, Object> params, Class<T> resClass) {
        try {
            if (svcURI == null || svcURI.equals("")) {
                LOG.error("svcURI为空");
                return null;
            }

            // 创建httpget
            String pstr = createLinkString(params);

            if (pstr.length() > 0) {
                if (svcURI.indexOf(CONNECTOR_1) == -1) {
                    svcURI = svcURI + CONNECTOR_1 + pstr;
                } else {
                    //uri中已经包含了部分参数
                    svcURI = svcURI + CONNECTOR_2 + pstr;
                }
            }

            LOG.debug("URI = {}", svcURI);

            HttpGet httpGet = new HttpGet(svcURI);
            httpGet.setConfig(this.requestConfig);
            // 执行get请求.
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity resEntity = response.getEntity();
            try {
                // 打印响应状态
                LOG.debug("response status code = {}", response.getStatusLine().getStatusCode());
                if (resEntity != null) {
                    return this.getRespData(resClass, response, resEntity);
                }
            } finally {
                EntityUtils.consume(resEntity);
                response.close();
                httpGet.releaseConnection();
            }
        } catch (Exception e) {
            LOG.error("{}", e);
        }

        return null;
    }

    @Override
    public <T> RespData<T> post(String svcURI, String contentType, String content, Class<T> resClass) {
        // 创建httppost
        HttpPost httpPost = new HttpPost(svcURI);
        httpPost.addHeader("Content-Type", contentType);
        LOG.debug("request msg body = {}", content);
        HttpEntity reqEntity = this.getEntity(content, contentType);
        return this.doRequest(httpPost, reqEntity, resClass);
    }

    @Override
    public <T> RespData<T> post(String svcURI, String contentType, Map<String, Object> params, Class<T> resClass) {
        HttpPost httpPost = new HttpPost(svcURI);
        HttpEntity reqEntity = this.getEntity(params);
        return this.doRequest(httpPost, reqEntity, resClass);
    }

    @Override
    public <T> RespData<T> put(String svcURI, Map<String, Object> params, Class<T> resClass) {
        HttpPut httpPut = new HttpPut(svcURI);
        HttpEntity reqEntity = this.getEntity(params);
        return this.doRequest(httpPut, reqEntity, resClass);
    }

    private <T> RespData<T> doRequest(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, HttpEntity reqEntity, Class<T> resClass) {
        try {
            httpEntityEnclosingRequestBase.setConfig(this.requestConfig);
            if (reqEntity != null)
                LOG.debug("POST数据: {}", reqEntity.toString());
            httpEntityEnclosingRequestBase.setEntity(reqEntity);
            CloseableHttpResponse response = httpClient.execute(httpEntityEnclosingRequestBase);
            HttpEntity resEntity = null;
            try {
                resEntity = response.getEntity();
                if (resEntity != null) {
                    return this.getRespData(resClass, response, resEntity);
                }
            } finally {
                EntityUtils.consume(resEntity);
                response.close();
                httpEntityEnclosingRequestBase.releaseConnection();
            }
        } catch (Exception e) {
            LOG.error("{}", e);
        }
        return null;
    }

    private HttpEntity getEntity(String content, String contentType) {
        if (content != null) {
            StringEntity stringEntity = new StringEntity(content, "UTF-8");
            stringEntity.setContentType(contentType);
            return stringEntity;
        }
        return null;
    }

    private <T> RespData<T> getRespData(Class<T> resClass, CloseableHttpResponse response, HttpEntity resEntity) {
        RespData<T> respData = new RespData<T>();
        try {
            StatusLine statusLine = response.getStatusLine();
            int statusCode = 0;
            if (statusLine != null) {
                statusCode = response.getStatusLine().getStatusCode();
            }
            if (response.getStatusLine() != null) {
                respData.setStatusCode(statusCode);
            } else {
                respData.setStatusCode(500);
            }
            String result = EntityUtils.toString(resEntity, "UTF-8");
            LOG.debug("POST结果: {}", result);
            try {
                respData.setData((T) JSONObject.parse(result));
            } catch (Exception e) {
                respData.setData((T) result);
            }
            return respData;
        } catch (Exception e) {
            LOG.error("{}", e);
        }
        return respData;
    }

    private HttpEntity getEntity(Map<String, Object> params) {
        try {
            List<NameValuePair> formParams = createParamPairs(params);
            return new UrlEncodedFormEntity(formParams, "UTF-8");
        } catch (Exception e) {
            LOG.error("{}", e);
        }
        return null;
    }

    private List<NameValuePair> createParamPairs(Map<String, Object> params) {
        List<NameValuePair> paramPairs = new ArrayList<>();
        if (params != null) {
            Iterator<String> iter = params.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                Object value = params.get(key);
                if (value != null) {
                    NameValuePair pair = new BasicNameValuePair(key, value.toString());
                    paramPairs.add(pair);
                }
            }
        }
        return paramPairs;
    }

    public String createLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String pstr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                pstr = pstr + key + "=" + value.toString();
            } else {
                pstr = pstr + key + "=" + value.toString() + "&";
            }
        }

        return pstr;
    }
}

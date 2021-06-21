package cn.nj.jwt.pojo;

import com.alibaba.fastjson.JSON;

/**
 * @author ：zty
 * @date ：Created in 2021/6/21 10:24
 * @description ： 日志请求对象
 */

public class LogRequsetInfo {


    private String ip;

    private String url;
    private String httpMethod;
    private String classMethod;

    private Object requestParams;
    private Object result;
    private Long timeCost;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public Object getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Object requestParams) {
        this.requestParams = requestParams;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Long timeCost) {
        this.timeCost = timeCost;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

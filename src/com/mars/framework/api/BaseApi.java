package com.mars.framework.api;


/**
 * 
 * 网络接口基类
 * 
 * @author Mars
 *
 */
public abstract class BaseApi {
    
    protected String baseUrl = "http:\\127.0.0.1";
    
    public BaseApi(){
    	configBaseUrl();
    }
    
    /**
     * 配置接口服务器
     */
    protected abstract void configBaseUrl();
    
    /**
     * 配置服务器Url
     * @return
     */
    protected String getBaseUrl(){
    	return baseUrl;
    }
    
}

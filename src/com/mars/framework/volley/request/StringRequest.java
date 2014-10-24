/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mars.framework.volley.request;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.text.TextUtils;

import com.mars.framework.volley.error.AuthFailureError;
import com.mars.framework.volley.net.HttpHeaderParser;
import com.mars.framework.volley.request.Request.Method;
import com.mars.framework.volley.response.NetworkResponse;
import com.mars.framework.volley.response.Response;
import com.mars.framework.volley.response.Response.ErrorListener;
import com.mars.framework.volley.response.Response.Listener;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 */
public class StringRequest extends Request<String> {
	
	 /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";
    
    private HashMap<String, String> params;
    
    private final Listener<String> mListener;
    
    /**
     * Creates a new GET request without params.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(String url, Listener<String> listener, ErrorListener errorListener) {
        this(Method.GET, url, null,listener, errorListener);
    }
    
    /**
     * Creates a new GET request with params.
     * 
     * @param url URL to fetch the string at
     * @param params 
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(String url, HashMap<String, String> params,Listener<String> listener, ErrorListener errorListener) {
        this(Method.GET, url, params,listener, errorListener);
    }
    
    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(int method, String url, HashMap<String, String> params,Listener<String> listener,
            ErrorListener errorListener) {
        super(method,(method == Method.GET ? getUrlWithQueryString(url, params) : url), errorListener);
        
        mListener = listener;
        if(params != null){
            this.params = params;
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        // TODO Auto-generated method stub
        return params;
    }

    @Override
    public void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
    
    /**
     * 根据参数凭借Url地址用于Get请求
     *
     * @param url
     * @param params
     * @return
     */
    private static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (!TextUtils.isEmpty(url) && params != null && params.size() != 0) {
            List<BasicNameValuePair> paramsList = new LinkedList<BasicNameValuePair>();
            for (ConcurrentHashMap.Entry<String, String> entry : params.entrySet()) {
                paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            String paramsString = URLEncodedUtils.format(paramsList, PROTOCOL_CHARSET);
            if (url.indexOf("?") == -1) {
                url += "?" + paramsString;
            } else {
                url += "&" + paramsString;
            }
        }
        return url;
    }
}

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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.text.TextUtils;

import com.mars.framework.volley.error.AuthFailureError;
import com.mars.framework.volley.request.Request.Method;
import com.mars.framework.volley.response.NetworkResponse;
import com.mars.framework.volley.response.Response;
import com.mars.framework.volley.response.Response.ErrorListener;
import com.mars.framework.volley.response.Response.Listener;

/**
 * A request for retrieving a T type response body at a given URL that also
 * optionally sends along a JSON body in the request specified.
 *
 * @param <T> JSON type of response expected
 */
public abstract class JsonRequest<T> extends Request<T> {
    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
        String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private final Listener<T> mListener;
    
    private final Map<String, String> mParmas;

    /**
     * Deprecated constructor for a JsonRequest which defaults to GET unless {@link #getPostBody()}
     * or {@link #getPostParams()} is overridden (which defaults to POST).
     *
     * @deprecated Use {@link #JsonRequest(int, String, String, Listener, ErrorListener)}.
     */
    public JsonRequest(String url, Map<String, String> requestParmas, Listener<T> listener,
            ErrorListener errorListener) {
        this(Method.DEPRECATED_GET_OR_POST, url, requestParmas, listener, errorListener);
    }
    

    public JsonRequest(int method, String url, Map<String, String> requestParmas, Listener<T> listener,
            ErrorListener errorListener) {
        super(method, method == Method.GET ? getUrlWithQueryString(url, requestParmas) : url, errorListener);
        
        mListener = listener;
        mParmas = requestParmas;
    }

    @Override
    public void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    abstract public Response<T> parseNetworkResponse(NetworkResponse response);

    /**
     * @deprecated Use {@link #getBodyContentType()}.
     */
    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        // TODO Auto-generated method stub
        return mParmas != null ? mParmas : super.getParams();
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

package com.mars.framework.volley.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.protocol.HTTP;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mars.framework.volley.error.AuthFailureError;
import com.mars.framework.volley.error.ParseError;
import com.mars.framework.volley.net.HttpHeaderParser;
import com.mars.framework.volley.response.NetworkResponse;
import com.mars.framework.volley.response.Response;

/**
 * 封装Google的Gson的数据请求，返回的直接是解析好的对象
 * @param <T>
 */
public class GsonRequest<T> extends Request<T> {

    private final Gson mGson;
    private final Response.Listener<T> mListener;
    private final Class<T> mClazz;

    private Map<String, String> mParmas;

    private Priority mPriority;

    /**
     * 创建一个规定方法，不带参数的GsonRequest
     *
     * @param method        Method.GET or Method.POST
     * @param clazz         需要解析成的对象
     * @param url           请求的Url
     * @param listener
     * @param errorListener
     */
    public GsonRequest(int method,Class<T> clazz, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, clazz, null, url, listener, errorListener);
    }

    /**
     * 创建一个规定方法，带参数的GsonRequest
     *
     * @param method        Method.GET or Method.POST
     * @param clazz         需要解析成的对象
     * @param parmas        请求的参数
     * @param url           请求的Url
     * @param listener
     * @param errorListener
     */
    public GsonRequest(int method, Class<T> clazz, Map<String, String> parmas, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, method == Method.GET ? getUrlWithQueryString(url, parmas) : url, errorListener);

        this.mGson = new Gson();
        this.mListener = listener;
        this.mClazz = clazz;
        this.mParmas = parmas;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParmas != null ? mParmas : super.getParams();
    }

    @Override
    public void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public Response<T> parseNetworkResponse(NetworkResponse response) {
        String contentEncoding = response.headers.get(HTTP.CONTENT_ENCODING);

        if(!TextUtils.isEmpty(contentEncoding) && contentEncoding.equals("gzip")){
            String output = "";
            try {
                GZIPInputStream gStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
                InputStreamReader reader = new InputStreamReader(gStream);
                BufferedReader in = new BufferedReader(reader);
                String read;
                while ((read = in.readLine()) != null) {
                    output += read;
                }
                reader.close();
                in.close();
                gStream.close();
            } catch (IOException e) {
                return Response.error(new ParseError());
            }
            return Response.success(mGson.fromJson(output, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        }else{
            try {
                String json = new String(
                        response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(
                        mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JsonSyntaxException e) {
                return Response.error(new ParseError(e));
            }
        }

    }

    /**
     * 设置访问优先级
     * @param priority
     */
    public void setPriority(Priority priority){
        mPriority = priority;
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }

}

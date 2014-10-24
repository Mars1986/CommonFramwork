package com.mars.framework.volley.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
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
 * Created by hongzhuo on 2014/4/16.
 * <p/>
 * For example: Request request = new
 */
public class GsonObjectRequest<T> extends Request<T> {

    private static final String PROTOCOL_CHARSET = "utf-8";

    private final Gson mGson;
    private final Response.Listener<T> mListener;
    private final Class<T> mClazz;

    private Map<String, String> mParmas;
    private Priority mPriority = Priority.NORMAL;

    /**
     * 带参数的 Post\Get 请求
     *
     * @param method        Method.GET or Method.POST
     * @param parmas
     * @param url
     * @param listener
     * @param errorListener
     */
    public GsonObjectRequest(int method, Class<T> clazz, Map<String, String> parmas, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, method == Method.GET ? getUrlWithQueryString(url, parmas) : url, errorListener);
        this.mGson = new Gson();
        this.mListener = listener;

        //http://www.blogjava.net/calvin/archive/2006/04/28/43830.html
//        this.mClazz = (Class<T>) ((ParameterizedType) ((Object) this).getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.mClazz = clazz;
        this.mParmas = parmas;
    }

    /**
     * 无参数的 Post\Get 请求
     *
     * @param method        Method.GET or Method.POST
     * @param url
     * @param listener
     * @param errorListener
     */
    public GsonObjectRequest(int method, Class<T> clazz, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, clazz, null, url, listener, errorListener);
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
     * 设置请求优先级
     * @param priority
     */
    public void setPriority(Priority priority){
        this.mPriority = priority;
    }

    @Override
    public Priority getPriority() {
        return mPriority;
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

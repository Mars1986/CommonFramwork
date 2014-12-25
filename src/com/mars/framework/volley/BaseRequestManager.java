package com.mars.framework.volley;

import android.content.Context;

import com.mars.framework.volley.request.Request;

/**
 * Created by hongzhuo on 2014/4/21.
 */
public abstract class BaseRequestManager {

	protected RequestQueue mRequestQueue = null;
//	protected ImageLoader.ImageCache mImageCache = null;
//	protected ImageLoader mImageLoader = null;

	public BaseRequestManager(Context context) {
		init(context);
	}

	/**
	 * 初始化入口
	 * 
	 * @param context
	 */
	public void init(Context context) {
		if (mRequestQueue == null) {
			mRequestQueue = initRequestQueue(context);
		}

		if (mRequestQueue == null ) {
            throw new IllegalArgumentException("initRequestQueue must not be Null");
		}

	}

	/**
	 * 添加Http请求至队列
	 * 
	 * @param request
	 */
	public void addHttpRequest(Request<?> request) {
		if (mRequestQueue != null) {
            mRequestQueue.add(request);
		}
	}
	
	/**
	 * 返回请求队列
	 * @return
	 */
	public RequestQueue getRequestQueue(){
		return mRequestQueue;
	}


	/**
	 * 返回请求队列
	 * 
	 * @param context
	 * @return
	 */
	public abstract RequestQueue initRequestQueue(Context context);

}

package com.mars.framework.volley;

import android.content.Context;
import android.graphics.Bitmap;

import com.mars.framework.image.ImageContainer;
import com.mars.framework.image.ImageDisplay;
import com.mars.framework.image.ImageLoader;
import com.mars.framework.image.ImageLoader.ImageListener;
import com.mars.framework.volley.request.Request;

/**
 * Created by hongzhuo on 2014/4/21.
 */
public abstract class BaseRequestManager {

	protected RequestQueue mRequestQueue = null;
//	protected ImageLoader.ImageCache mImageCache = null;
//	protected ImageLoader mImageLoader = null;
	
	private ImageLoader mSohuImageLoader;

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

		if(mSohuImageLoader == null){
			initImageLoader(context);
		}
	}

	/**
	 * 添加Http请求至队列
	 * 
	 * @param request
	 */
	public void addHttpRequest(Request request) {
		if (mRequestQueue != null) {
            mRequestQueue.add(request);
		}
	}

	/**
	 * 执行图片加载
	 * 
	 * @param url
	 * @param width
	 * @param height
	 * @param listener
	 * @return
	 */
	public Bitmap loadImage(String url, int width, int height, ImageListener listener, boolean isCache) {
		if (listener != null && listener instanceof ImageDisplay) {
			((ImageDisplay) listener).display(mSohuImageLoader, url, width, height, isCache);
			return null;
		} else {
			ImageContainer container = new ImageContainer(url, 0);
			container.initImageInfo(width, height);
			return mSohuImageLoader.getImage(container, listener, null, isCache);
		}
	}
	
	/**
	 * 加载Launcher首页图片
	 * 
	 * @param container
	 * @param listener
	 * @param isCache
	 * @return
	 */
	public Bitmap loadHomeImage(ImageContainer container, ImageListener listener, boolean isCache) {
		return mSohuImageLoader.getHomeImage(container, listener, isCache);
	}
	

	/**
	 * 返回请求队列
	 * 
	 * @param context
	 * @return
	 */
	public abstract RequestQueue initRequestQueue(Context context);

	public void initImageLoader(Context context){
		mSohuImageLoader = new ImageLoader(context);
		mSohuImageLoader.start();
	}
	
	public ImageLoader getImageLoader(){
		return this.mSohuImageLoader;
	}
}

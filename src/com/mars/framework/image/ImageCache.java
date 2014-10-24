package com.mars.framework.image;

import android.graphics.Bitmap;

/**
 * Launcher图片缓存，包括home页图片缓存及非home页的LRU缓存
 * 
 * @author Jason
 * 
 */
public class ImageCache {

	@SuppressWarnings("unused")
	private static final String TAG = "ImageCache";

	public static final int CACHE_TYPE_HOME = 1;

	public static final int CACHE_TYPE_LRU = 2;

	private final ImageLruCache mImageLruCache;

	// private final HomePageImageCache mHomeImageCache;

	public ImageCache(int lruSize) {
		mImageLruCache = new ImageLruCache(lruSize);
		//mHomeImageCache = new HomePageImageCache();
	}

	/**
	 * 
	 * @param url
	 * @param cacheType
	 * @return
	 */
	public Bitmap get(String url, int cacheType) {
		if (CACHE_TYPE_HOME == cacheType) {
			//return mHomeImageCache.get(url);
			return null;
		} else if (CACHE_TYPE_LRU == cacheType) {
			return mImageLruCache.getImage(url);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param value
	 * @param image
	 * @param cacheType
	 */
	public Bitmap put(ImageContainer key, Bitmap image, int cacheType) {
		// String url = (key == null ? null : key.getUrl());
		if (CACHE_TYPE_HOME == cacheType) {
			// return mHomeImageCache.put(url, image);
			return null;
		} else if (CACHE_TYPE_LRU == cacheType) {
			return mImageLruCache.putImage(key, image);
		}
		return null;
	}

	/**
	 * 
	 * @param cacheType
	 */
	public void clear(int cacheType) {
		if (CACHE_TYPE_HOME == cacheType) {
			//mHomeImageCache.clear();
		} else if (CACHE_TYPE_LRU == cacheType) {
			mImageLruCache.evictAll();
		}
	}
	
	/**
	 * 删除Cache中某一个元素
	 * 
	 * @param url
	 * @param cacheType
	 * @return
	 */
	public boolean remove(String url, int cacheType) {
		if (CACHE_TYPE_HOME == cacheType) {
			// return mHomeImageCache.delete(url);
			return false;
		} else if (CACHE_TYPE_LRU == cacheType) {
			return mImageLruCache.removeImage(url);
		}
		return false;
	}
	
	/**
	 * 删除Cache中元素，只保留maxSize数量.该接口只针对LRU
	 * 
	 * @param maxSize
	 */
	public void remove(int maxSize) {
		mImageLruCache.trimToSize(maxSize);
	}
	
	/**
	 * 
	 * @param url
	 * @param cacheType
	 * @return
	 */
	public boolean isExist(String url, int cacheType) {
		// if (CACHE_TYPE_HOME == cacheType) {
		// return mHomeImageCache.containsKey(url);
		// } else if (CACHE_TYPE_LRU == cacheType) {
		// // LRU不支持是否存在数据的查询
		// }
		return false;
	}

}

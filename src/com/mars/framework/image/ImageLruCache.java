package com.mars.framework.image;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 图片内存LRU缓存
 * 
 * @author Jason
 * 
 */
public class ImageLruCache extends LruCache<String, ImageContainer> {
	
	private static final String TAG = "ImageLruCache";
	
	public ImageLruCache(int maxSize) {
		super(maxSize);
	}
	
	public void entryRemoved(boolean evicted, String key, ImageContainer oldValue, ImageContainer newValue) {
		Logger.d(TAG, "Current image LRU size: " + super.size());
		if (evicted) {
			oldValue.release();
		}
	}

	public int sizeOf(String key, ImageContainer value) {
		return 1;
	}

	public Bitmap getImage(String url) {
		ImageContainer container = null;
		Bitmap image = null;
		if (url != null) {
			container = super.get(url);
		}
		if (container != null) {
			image = container.getImageData();
		}
		return image;
	}

	public ImageContainer create(String key) {
		return null;
	}

	public Bitmap putImage(ImageContainer value, Bitmap image) {
		if (value == null || value.getUrl() == null) {
			Logger.w(TAG, "Put new image haven't key in the LRU!");
			ImageUtil.imageRecycle(image);
			return null;
		} else if (image == null || image.isRecycled()) {
			return null;
		}
		ImageContainer oldContainer = super.put(value.getUrl(), value);
		if (oldContainer != null) {
			synchronized (this) {
				// 如果LRU中存在该资源，将旧资源图片存入新资源，并释放新图片
				if (oldContainer.isValid()) {
					Logger.d(TAG, "---This image is exist and valid!---");
					Bitmap oldImage = oldContainer.getImageData();
					value.setImageData(oldImage);
					oldContainer.release();
					ImageUtil.imageRecycle(image);
					if (oldImage == null) {
						Logger.w(TAG, "Return exist image is null! ");
					} else if (oldImage.isRecycled()) {
						Logger.w(TAG, "Return exist image is recycled! ");
					}
					return oldImage;
				} else {
					Logger.w(TAG, "---This image is exist and invalid!---");
					oldContainer = null;
					value.setImageData(image);
					return image;
				}
			}
		} else {
			Logger.d(TAG, "This image is latest!");
			value.setImageData(image);
			return image;
		}
	}
	
	public boolean removeImage(String key) {
		ImageContainer oldContainer = super.remove(key);
		synchronized (this) {
			if (oldContainer != null) {
				oldContainer.release();
				return true;
			}
		}
		return false;
	}
	
}

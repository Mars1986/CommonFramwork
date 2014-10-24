package com.mars.framework.image;

import android.graphics.Bitmap;

/**
 * 图片存储容器
 * 
 * @author Jason
 * 
 */
public class ImageContainer {
	
	private static final String TAG = "ImageContainer";

	private String mUrl;

	private Bitmap mImageData;

	// 如果图片被释放，显示默认图片
	private int mDefaultResId;

	private int mWidth;

	private int mHeight;

	public ImageContainer(String url, int defaultResId) {
		this.mUrl = url;
		this.mDefaultResId = defaultResId;
	}

	public void initImageInfo(int width, int height) {
		this.mWidth = width;
		this.mHeight = height;
	}

	protected void setImageData(Bitmap imageData) {
		if (imageData == null || imageData.isRecycled()) {
			return;
		}
		Logger.d(TAG, "Before put new image, recycle old image!");
		ImageUtil.imageRecycle(mImageData);
		this.mImageData = imageData;
	}

	public Bitmap getImageData() {
		return mImageData;
	}

	public String getUrl() {
		return mUrl;
	}

	public int getDefaultResId() {
		return mDefaultResId;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	public boolean isValid() {
		return mImageData != null && !mImageData.isRecycled();
	}

	public void release() {
		this.mImageData = null;
		// try {
		// if (mImageData != null && !mImageData.isRecycled()) {
		// Logger.w(TAG, "---Image recycle!---" + mUrl);
		// mImageData.recycle();
		// }
		// } catch (RuntimeException e) {
		// Logger.e(TAG, "Recycle image fail!", e);
		// }
		// mImageData = null;
	}

}

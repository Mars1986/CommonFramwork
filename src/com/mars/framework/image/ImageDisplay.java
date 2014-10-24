package com.mars.framework.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.mars.framework.image.ImageLoader.ImageListener;

/**
 * 
 * @author Jason
 * 
 */
public class ImageDisplay implements ImageListener {
	
	private static final String TAG = "ImageDisplay";

	private ImageView mView;

	private int mDefaultImageResId;

	@SuppressWarnings("unused")
	private String mImageUrl;

	public ImageDisplay(ImageView view, int defaultImageResId) {
		this.mView = view;
		this.mDefaultImageResId = defaultImageResId;
	}

	/**
	 * 
	 * @param sohuImageLoader
	 * @param url
	 * @param width
	 * @param height
	 */
	public void display(ImageLoader sohuImageLoader, String url, int width, int height, boolean isCache) {
		if (sohuImageLoader == null || url == null || mView == null) {
			return;
		}
		this.mImageUrl = url;
		ImageContainer container = new ImageContainer(url, mDefaultImageResId);
		container.initImageInfo(width, height);
		Bitmap image = sohuImageLoader.getImage(container, this, mView, isCache);
		if (image != null) {
			mView.setImageBitmap(image);
		} else {
			mView.setImageResource(mDefaultImageResId);
		}
	}

	@Override
	public void loadFinish(Bitmap image, String from) {
		Logger.d(TAG, "Load image finish! From " + from);
		if (mView == null) {
			return;
		}
		if (image == null) {
			Logger.w(TAG, "---After load, image is null!---");
		}
		if (image != null && !image.isRecycled()) {
			mView.setImageBitmap(image);
		} else {
			Logger.w(TAG, "---Image invalid!---" + (image == null) + "," + (image != null ? image.isRecycled() : false));
		}
	}

}

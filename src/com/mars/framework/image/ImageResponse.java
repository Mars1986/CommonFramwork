package com.mars.framework.image;

import android.graphics.Bitmap;

import com.mars.framework.image.ImageLoader.ImageListener;

/**
 * 
 * @author Jason
 * 
 */
public class ImageResponse {

	private ImageListener mListener;

	private Bitmap mImage;

	private String mFrom;

	public enum ImageFrom {
		NET, DISK, MEM;
	}

	public ImageResponse(ImageListener listener, Bitmap image, String from) {
		this.mListener = listener;
		this.mImage = image;
		this.mFrom = from;
	}

	public void execute() {
		if (mListener != null) {
			mListener.loadFinish(mImage, mFrom);
		}
	}

}

package com.mars.framework.image;

import android.graphics.Bitmap;

import com.mars.framework.image.ImageResponse.ImageFrom;
import com.mars.framework.image.executor.ITask;
import com.mars.framework.image.executor.TaskStateListener;

/**
 * 下载图片并缓存本地任务
 * 
 * @author Jason
 * 
 */
public class ImageDownloadTask extends ITask<Bitmap> {
	
	private static final String TAG = "ImageDownloadTask";

	private DiskRequest mDiskRequest;

	private String mImageUrl;
	
	private int mWidth;

	private int mHeight;

	/**
	 * 
	 * @param listener
	 * @param diskRequest
	 * @param imageUrl
	 * @param width
	 * @param height
	 */
	public ImageDownloadTask(TaskStateListener<Bitmap> listener, DiskRequest diskRequest, String imageUrl, int width, int height) {
		super(listener);
		this.mDiskRequest = diskRequest;
		this.mImageUrl = imageUrl;
		this.mWidth = width;
		this.mHeight = height;
	}

	@Override
	public void execute() {
		Bitmap image = null;
		// 下载前再次查询磁盘是否有此图片，尽量避免重复下载
		if (mDiskRequest != null) {
			image = mDiskRequest.query(mImageUrl, mWidth, mHeight);
			if (image != null) {
				super.sendState(image, ImageFrom.DISK.toString());
				return;
			}
		}
		// 下载图片
		byte[] imageData = ImageUtil.download(mImageUrl);
		Logger.d(TAG, "Download image from net! " + mImageUrl);
		if (imageData != null) {
			// 将下载的原图保存磁盘
			if (mDiskRequest != null) {
				mDiskRequest.save(mImageUrl, imageData);
			}
			image = ImageUtil.decodeBitmapFromByte(imageData, mWidth, mHeight);
			imageData = null;
		}
		super.sendState(image, ImageFrom.NET.toString());
	}
	
	@Override
	public boolean diskExecute() {
		if (mDiskRequest == null) {
			return false;
		}
		Bitmap image = mDiskRequest.query(mImageUrl, mWidth, mHeight);
		if (image != null) {
			super.sendState(image, ImageFrom.DISK.toString());
			return true;
		}
		return false;
	}

}

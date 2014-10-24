package com.mars.framework.image;

import java.util.List;

import android.graphics.Bitmap;
import android.os.Process;
import android.text.TextUtils;

import com.mars.framework.image.ImageLoader.SohuImagePreGetListener;
import com.mars.framework.image.executor.ExecutorDelivery;

/**
 * UI展示前图片从本地磁盘预取任务
 * 
 * @author Jason
 * 
 */
public class ImagePreStoreTask extends Thread {
	
	private static final String TAG = "ImagePreStoreTask";
	
	private SohuImagePreGetListener mListener;

	private DiskRequest mDiskRequest;

	private List<ImageContainer> mImageList;

	private ImageCache mImageCache;
	
	private ExecutorDelivery mExecutorDelivery;
	
	private boolean mIsRun = true;

	public ImagePreStoreTask(SohuImagePreGetListener listener, List<ImageContainer> imageList,
			DiskRequest diskRequest, ImageCache imageCache, ExecutorDelivery delivery) {
		this.mListener = listener;
		this.mImageList = imageList;
		this.mDiskRequest = diskRequest;
		this.mImageCache = imageCache;
		this.mExecutorDelivery = delivery;
	}

	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_LOWEST);
		if (mImageList == null || mImageList.isEmpty()) {
			return;
		}
		boolean isNotice = true;
		for (ImageContainer container : mImageList) {
			if (!mIsRun) {
				isNotice = false;
				break;
			}
			if (container == null || TextUtils.isEmpty(container.getUrl())) {
				continue;
			}
			Bitmap image = mDiskRequest.query(container.getUrl(), container.getWidth(), container.getHeight());
			Logger.w(TAG, "Image url: " + container.getUrl() + ", exist result:" + (image != null));
			if (image != null) {
				mImageCache.put(container, image, ImageCache.CACHE_TYPE_HOME);
			}
		}
		if (isNotice) {
			mExecutorDelivery.postOther(new NotifyUIRunnable());
		}
	}

	private class NotifyUIRunnable implements Runnable {
		public void run() {
			if (mListener != null) {
				mListener.loadFinish();
			}
		}
	}
	
	public void breakTask() {
		mIsRun = false;
	}

}

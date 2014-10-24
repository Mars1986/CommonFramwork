package com.mars.framework.image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.mars.framework.image.ImageResponse.ImageFrom;
import com.mars.framework.image.executor.ExecutorDelivery;
import com.mars.framework.image.executor.ITask;
import com.mars.framework.image.executor.RequestManager;
import com.mars.framework.image.executor.TaskStateListener;

/**
 * 图片加载器
 * 
 * @author Mars
 *
 */
public class ImageLoader {
	
	private static final String TAG = "SohuImageLoader";

	private static final String DEFAULT_IMAGE_DIR = "image";

	private static final String IMAGE_SUFFIX = ".jpg";
	
	private static final int CACHE_MAX_SIZE = 100;

	private final ImageCache mImageCache;

	private RequestManager mRequestManager;

	private DiskRequest mDiskRequest;

	private Context mContext;
	
	private ExecutorDelivery mExecutorDelivery;
	
	private File mRootDirectory;
	
	private List<ImagePreStoreTask> mPreStoreTaskList;
	
	public interface ImageListener {

		public void loadFinish(Bitmap image, String from);

	}

	public interface SohuImagePreGetListener {

		public void loadFinish();

	}

	public ImageLoader(Context context) {
		this.mContext = context;
		mRequestManager = new RequestManager();
		mExecutorDelivery = new ExecutorDelivery(new Handler(Looper.getMainLooper()));
		mImageCache = new ImageCache(CACHE_MAX_SIZE);
		mPreStoreTaskList = new ArrayList<ImagePreStoreTask>();
	}

	/**
	 * 根据图片URL及所需尺寸获取图片bitmap
	 * 
	 * @param imageContainer
	 * @param lintener
	 * @param view
	 * @param isCache
	 * @return
	 */
	public Bitmap getImage(ImageContainer imageContainer, ImageListener lintener, ImageView view, boolean isCache) {
		if (imageContainer == null || imageContainer.getUrl() == null) {
			return null;
		}
		return this.get(imageContainer, lintener, isCache, ImageCache.CACHE_TYPE_LRU);
	}
	
	/**
	 * 根据图片URL及所需尺寸为launcher首页获取图片bitmap
	 * 
	 * @param imageContainer
	 * @param lintener
	 * @param isCache
	 * @return
	 */
	public Bitmap getHomeImage(ImageContainer imageContainer, ImageListener lintener, boolean isCache){
		if (imageContainer == null || imageContainer.getUrl() == null) {
			return null;
		}
		return this.get(imageContainer, lintener, isCache, ImageCache.CACHE_TYPE_LRU);
	}
	
	/**
	 * 界面渲染前从本地磁盘预期图片
	 * 
	 * @param imageList
	 */
	public void preGetImage(List<ImageContainer> imageList, SohuImagePreGetListener listener) {
//		if (imageList == null) {
//			return;
//		}
//		int size = imageList.size();
//		for (int i = 0; i < size; i++) {
//			String url = imageList.get(0).getUrl();
//			if (mImageCache.isExist(url, ImageCache.CACHE_TYPE_HOME)) {
//				imageList.remove(0);
//			}
//		}
//		if (imageList != null && imageList.size() > 0) {
//			ImagePreStoreTask task = new ImagePreStoreTask(listener, imageList, mDiskRequest, mImageCache, mExecutorDelivery);
//			mPreStoreTaskList.add(task);
//			task.setName("Image_PreStore_Task");
//			task.start();
//		} else {
//			Logger.d(TAG, "Prestore image is null!");
//			if (listener != null) {
//				listener.loadFinish();
//			}
//		}
	}
	
	/**
	 * 
	 * @param imageContainer
	 * @param lintener
	 * @param isCache
	 * @param cacheType
	 * @return
	 */
	private Bitmap get(final ImageContainer imageContainer, final ImageListener lintener, final boolean isCache, final int cacheType) {
		Logger.d(TAG, "Get image by " + cacheType);
		String url = imageContainer.getUrl();
		int width = imageContainer.getWidth();
		int height = imageContainer.getHeight();
		Bitmap diskImage = null;
		if (isCache) {
			diskImage = mImageCache.get(url, cacheType);
		}
		if (diskImage != null) {
			Logger.i(TAG, "Get image from " + ImageFrom.MEM.toString());
			return diskImage;
		}

		ITask<Bitmap> task = new ImageDownloadTask(new TaskStateListener<Bitmap>() {
				public void executeResult(Bitmap result, String... bakup) {
					Logger.d(TAG, "Get image success! " + imageContainer.getUrl());
					String from = (bakup == null || bakup.length <= 0) ? null : bakup[0];
					sendResult(lintener, result, imageContainer, from, isCache, cacheType);
				}
			}, mDiskRequest, url, width, height);
		mRequestManager.addTask(task, true);
		return null;
	}

	/**
	 * 开启图片加载器
	 */
	public void start() {
		this.initDisk();
		mRequestManager.start();
	}

	/**
	 * 初始化缓存磁盘
	 */
	private void initDisk() {
		mRootDirectory = new File(mContext.getCacheDir(), DEFAULT_IMAGE_DIR);
		if (!mRootDirectory.exists()) {
			mRootDirectory.mkdirs();
		}
		mDiskRequest = new DiskRequest(mRootDirectory, IMAGE_SUFFIX);
	}

	/**
	 * 关闭图片加载器
	 */
	public void stop() {
		mRequestManager.stop();
	}
	
	/**
	 * 清空任务队列
	 */
	public void cancelTask() {
		mRequestManager.cancelTask();
		this.cancelPreStore();
	}

	/**
	 * 清空所有缓存中数据
	 * 
	 * @param cacheType
	 */
	public void clearCache(int cacheType) {
		mImageCache.clear(cacheType);
	}
	
	public boolean removeCache(String url) {
		return mImageCache.remove(url, ImageCache.CACHE_TYPE_LRU);
	}

	public void removeCache(int maxSize) {
		mImageCache.remove(maxSize);
	}

	/**
	 * 发送结果给调用者
	 * 
	 * @param lintener
	 * @param image
	 * @param imageContainer
	 * @param from
	 * @param isCache
	 * @param cacheType
	 */
	private void sendResult(ImageListener lintener, Bitmap image, ImageContainer imageContainer,
			String from, boolean isCache, int cacheType) {
		if (lintener == null || image == null) {
			Logger.w(TAG, "---Download image error!---");
			return;
		}
		Logger.i(TAG, "Load image from " + from + ", url:" + imageContainer.getUrl());
		Bitmap cacheDate = null;
		if (isCache) {
			cacheDate = mImageCache.put(imageContainer, image, cacheType);
		} else {
			cacheDate = image;
		}
		ImageResponse response = new ImageResponse(lintener, cacheDate, from);
		mExecutorDelivery.postResponse(response);
	}
	
	/**
	 * 切换页面时清除预取任务
	 */
	private void cancelPreStore() {
		if (mPreStoreTaskList == null || mPreStoreTaskList.isEmpty()) {
			return;
		}
		for (ImagePreStoreTask task : mPreStoreTaskList) {
			if (task != null && task.isAlive()) {
				task.breakTask();
			}
		}
		mPreStoreTaskList.clear();
	}
	

}

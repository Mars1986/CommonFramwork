package com.mars.framework.image.executor;

import java.util.concurrent.LinkedBlockingQueue;

import com.mars.framework.image.DiskDispatcher;
import com.mars.framework.image.Logger;

/**
 * 管理线程池及任务分配
 * 
 * @author Jason
 * 
 */
public class RequestManager {
	
	private static final String TAG = "RequestManager";

	private final LinkedBlockingQueue<ITask<?>> mRequestQueue = new LinkedBlockingQueue<ITask<?>>();
	
	private final LinkedBlockingQueue<ITask<?>> mCacheRequestQueue = new LinkedBlockingQueue<ITask<?>>();

	private static final int DEFAULT_THREAD_POOL_SIZE = 2;

	private static final String TASK_NAME = "Jason-Thread-Pool";
	
	private RequestExecutor[] mExecutors;
	
	private DiskDispatcher mDiskDispatcher;

	private boolean isStart = false;

	public RequestManager(int threadPoolSize) {
		this.initThreadPool(threadPoolSize);
	}

	public RequestManager() {
		this.initThreadPool(DEFAULT_THREAD_POOL_SIZE);
	}

	private void initThreadPool(int threadPoolSize) {
		this.mExecutors = new RequestExecutor[threadPoolSize];
	}

	/**
	 * 开启线程池
	 */
	public synchronized void start() {
		Logger.i(TAG, "Start request thread pool!");
		if (mDiskDispatcher != null) {
			mDiskDispatcher.quit();
		}
		mDiskDispatcher = new DiskDispatcher(mRequestQueue, mCacheRequestQueue);
		mDiskDispatcher.setName(TASK_NAME);
		mDiskDispatcher.start();
		for (int i = 0; i < mExecutors.length; i++) {
			if (mExecutors[i] != null) {
				mExecutors[i].quit();
			}
			String name = TASK_NAME + (i + 1);
			RequestExecutor executor = new RequestExecutor(mRequestQueue, name);
			mExecutors[i] = executor;
			executor.start();
			isStart = true;
		}
	}

	/**
	 * 暂停线程池
	 */
	public synchronized void stop() {
		Logger.i(TAG, "Stop request thread pool!");
		if (mDiskDispatcher != null) {
			mDiskDispatcher.quit();
		}
		for (int i = 0; i < mExecutors.length; i++) {
			if (mExecutors[i] != null) {
				mExecutors[i].quit();
			}
			isStart = false;
		}
	}

	/**
	 * 添加任务
	 * 
	 * @param task
	 */
	public void addTask(ITask<?> task, boolean isCache) {
		Logger.i(TAG, "Add task to thread pool!");
		if (task == null || !isStart) {
			return;
		}
		if (isCache) {
			mCacheRequestQueue.add(task);
		} else {
			mRequestQueue.add(task);
		}
	}
	
	/**
	 * 清空任务队列
	 */
	public void cancelTask() {
		try {
			mCacheRequestQueue.clear();
			mRequestQueue.clear();
		} catch (RuntimeException e) {
		}
	}

}

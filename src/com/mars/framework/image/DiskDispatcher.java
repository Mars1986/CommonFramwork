package com.mars.framework.image;

import java.util.concurrent.BlockingQueue;

import android.os.Process;

import com.mars.framework.image.executor.ITask;
import com.mars.framework.log.LogManager;

/**
 * 磁盘操作线程
 * 
 * @author Mars
 *
 */
public class DiskDispatcher extends Thread {

	private static final String TAG = "DiskDispatcher";

	private final BlockingQueue<ITask<?>> mNetQueue;

	private final BlockingQueue<ITask<?>> mCacheQueue;

	private volatile boolean mQuit = false;

	public DiskDispatcher(BlockingQueue<ITask<?>> netQueue, BlockingQueue<ITask<?>> cacheQueue) {
		this.mNetQueue = netQueue;
		this.mCacheQueue = cacheQueue;
	}

	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		ITask<?> task;
		while (true) {
			try {
				task = mCacheQueue.take();
			} catch (InterruptedException e) {
				if (mQuit) {
					return;
				}
				continue;
			}
			boolean isExe = false;
			try {
				if (task != null) {
					isExe = task.diskExecute();
				}
			} catch (RuntimeException e) {
				LogManager.e(TAG, "Execute cache task error!", e);
			}
			if (!isExe) {
				mNetQueue.add(task);
			}
		}
	}

	public void quit() {
		mQuit = true;
		interrupt();
	}

}

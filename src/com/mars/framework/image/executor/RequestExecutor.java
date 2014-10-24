package com.mars.framework.image.executor;

import java.util.concurrent.BlockingQueue;

import android.os.Process;

import com.mars.framework.image.Logger;

/**
 * 执行任务线程
 * 
 * @author Jason
 * 
 */
public class RequestExecutor extends Thread {
	
	private static final String TAG = "RequestExecutor";

	private volatile boolean mQuit = false;

	private final BlockingQueue<ITask<?>> mQueue;

	public RequestExecutor(BlockingQueue<ITask<?>> queue, String taskType) {
		this.mQueue = queue;
		this.setName(taskType);
	}

	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		ITask<?> task;
		while (true) {
			try {
				task = mQueue.take();
			} catch (InterruptedException e) {
				if (mQuit) {
					return;
				}
				continue;
			}
			try {
				task.execute();
			} catch (RuntimeException e) {
				Logger.e(TAG, "Execute task error!", e);
			}
		}

	}

	public void quit() {
		mQuit = true;
		interrupt();
	}

}

package com.mars.framework.image.executor;

import java.util.concurrent.Executor;

import android.os.Handler;

import com.mars.framework.image.ImageResponse;

/**
 * 通过Handler指派任务在主线程去运行
 * 
 * @author Jason
 * 
 */
public class ExecutorDelivery {

	private final Executor mPoster;

	public ExecutorDelivery(final Handler handler) {
		mPoster = new Executor() {
			@Override
			public void execute(Runnable command) {
				handler.post(command);
			}
		};
	}

	/**
	 * 指派传入的任务去主线程运行.
	 * 
	 * @param task
	 */
	public void postOther(Runnable task) {
		if (task != null) {
			mPoster.execute(task);
		}
	}

	/**
	 * 指派回调任务去主线程运行
	 */
	public void postResponse(ImageResponse response) {
		mPoster.execute(new DeliveryRunnable(response));
	}

	private class DeliveryRunnable implements Runnable {

		private ImageResponse mResponse;

		public DeliveryRunnable(ImageResponse response) {
			this.mResponse = response;
		}

		public void run() {
			mResponse.execute();
		}
	}

}

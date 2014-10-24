package com.mars.framework.image.executor;

/**
 * 线程池可接受任务父类
 * 
 * @author Jason
 * 
 */
public abstract class ITask<E> {

	protected TaskStateListener<E> mListener;

	public ITask(TaskStateListener<E> lintener) {
		this.mListener = lintener;
	}

	/**
	 * 执行任务(如果磁盘中无数据，从网络取数据)
	 */
	public abstract void execute();
	
	/**
	 * 执行磁盘任务(优先从提盘提取数据)
	 * 
	 * @return
	 */
	public abstract boolean diskExecute();

	/**
	 * 通过回调发送执行结果
	 * 
	 * @param resutl
	 */
	protected void sendState(E resutl, String from) {
		if (mListener != null) {
			mListener.executeResult(resutl, from);
		}
	}

	/**
	 * 当前使用PriorityBlockingQueue容器，比较返回0，采用后来者先执行的策略。
	 */
	public int compareTo(ITask<E> other) {
		return 0;
	}

}

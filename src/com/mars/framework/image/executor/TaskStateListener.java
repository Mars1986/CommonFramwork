package com.mars.framework.image.executor;

/**
 * 返回任务执行结果回调接口
 * 
 * @author Jason
 * 
 * @param <E>
 */
public interface TaskStateListener<E> {

	public void executeResult(E result, String... bakup);

}

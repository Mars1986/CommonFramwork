package com.mars.framework.image.executor;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Jason
 * 
 * @param <E>
 */
public class TaskQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>,
		java.io.Serializable {

	private static final long serialVersionUID = 8180166743421262357L;

	public boolean add(E e) {
		return offer(e);
	}

	@Override
	public boolean offer(E e) {
		return false;
	}

	@Override
	public E poll() {
		return null;
	}

	@Override
	public E peek() {
		return null;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public void put(E e) throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit)
			throws InterruptedException {
		return false;
	}

	@Override
	public E take() throws InterruptedException {
		return null;
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		return null;
	}

	@Override
	public int remainingCapacity() {
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		return 0;
	}

}

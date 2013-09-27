package com.eu.remote;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象池类〄1�7
 * 
 * @author Administrator
 * @param <T>
 */

public class ObjectPool<T> implements ObjectFactory<T> {

	private ObjectFactory<T> factory;
	private int maxCount;
	private List<T> objPool;

	public ObjectPool(ObjectFactory<T> factory, int maxCount) {
		super();
		this.factory = factory;
		this.maxCount = maxCount;
		objPool = new ArrayList<T>(maxCount);
	}

	/**
	 * 获取新对豄1�7 if pool has unused obj reuse it or recreate it
	 * 
	 * @return
	 */
	@Override
	public T newObject() {

		if (objPool.size() > 0) {
			return objPool.remove(objPool.size() - 1);

		} else {
			if (factory != null)
				return factory.newObject();
			else
				return null;
		}

	}

	/**
	 * release an obj make it unuse if no bigger than the maxCount add to pool<list> or simply drop it , let gc help him.
	 * 
	 * @param data
	 */
	public void release(T data) {
		if (objPool.size() < maxCount) {
			objPool.add(data);
		}

	}

	public void clear() {
		objPool.clear();

	}

}

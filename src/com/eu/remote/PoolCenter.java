package com.eu.remote;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取对象池的公共的类〄1�7
 * 
 * 这个籄1�7 每个方法 获取特定对象汄1�7 
 * 
 * 可以考虑在entity 中添加静态方泄1�7 去获取对象池〄1�7
 * @author Administrator
 *
 */

/**
 * 对象池控制中忄1�7
 * 
 * @author Administrator
 * 
 */
public class PoolCenter {

	private static final Map<Class<?>, ObjectPool<?>> pools = new HashMap<Class<?>, ObjectPool<?>>();

	public synchronized static final ObjectPool getObjectPool(final Class<?> c) {
		ObjectPool result = null;
		if (pools.containsKey(c)) {
			result = (ObjectPool) pools.get(c);
		}

		if (result == null) {

			result = new ObjectPool(getObjectFactory(c), 20);
			pools.put(c, result);
		}

		return result;

	}

	private static final Map<Class<?>, ObjectFactory<?>> factorys = new HashMap<Class<?>, ObjectFactory<?>>();

	public synchronized static final ObjectFactory getObjectFactory(
			final Class<?> c) {
		ObjectFactory<?> result = null;
		if (factorys.containsKey(c)) {
			result = factorys.get(c);
		}

		if (result == null) {

			result = new ObjectFactory() {

				@Override
				public Object newObject() {
					// TODO Auto-generated method stub
					try {
						return c.newInstance();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					throw new RuntimeException(new StringBuilder(
							"error on create new entity class  :").append(c)
							.append(",check your code, it must have an empty constructor").toString());
				}
			};

			factorys.put(c, result);
		}

		return result;

	}

	// private static ObjectPool<Entity_EmployeeInfo> employeePool;
	//
	// /**
	// * 获取Entity_Employee pool
	// *
	// * @return
	// */
	// public static ObjectPool<Entity_EmployeeInfo>
	// getEntity_EmployeeInfoPool() {
	//
	// if (employeePool == null)
	//
	// employeePool = new ObjectPool<Entity_EmployeeInfo>(
	// new ObjectFactory<Entity_EmployeeInfo>() {
	//
	// @Override
	// public Entity_EmployeeInfo newObject() {
	// // TODO Auto-generated method stub
	// return new Entity_EmployeeInfo();
	// }
	// }, 20);
	//
	// return employeePool;
	// }
	//
	// private static ObjectPool<Entity_Login> entity_LoginPool;
	//
	// /**
	// * 获取Entity_Login pool
	// *
	// * @return
	// */
	// public static ObjectPool<Entity_Login> getEntity_LoginPool() {
	//
	// if (entity_LoginPool == null)
	//
	// entity_LoginPool = new ObjectPool<Entity_Login>(
	// new ObjectFactory<Entity_Login>() {
	//
	// @Override
	// public Entity_Login newObject() {
	// // TODO Auto-generated method stub
	// return new Entity_Login();
	// }
	// }, 20);
	//
	// return entity_LoginPool;
	// }

}

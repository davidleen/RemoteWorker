package com.eu.remote;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.remotedataworker.BuildConfig;

/**
 * 数据解析籄1�7 利用java 反射 自动映射对象
 * 
 * @author davidleen29
 * @param <T>
 */
public class JSONParser<T> {

	private static final String TAG = "JSONParser";
	private static final boolean SEE_REFLECT = true;// 是否查看日志反射失败的时倄1�7

	private Class<?> dataClass;// 内置的泛型实际数据类。

	public JSONParser(Class<?> dataClass) {
		this.dataClass = dataClass;
	}

	public JSONParser() {

	}

	/**
	 * 数据解析 将 json string 解析成 resultClass 对象
	 * 
	 * @param jsonString
	 * @param resultClass
	 * @return
	 * @throws JSONException
	 */
	public T parse(String jsonString, Class<?> resultClass) throws JSONException {

		JSONObject json = new JSONObject(jsonString);
		T obj;
		try {
			obj = (T) resultClass.newInstance();
		} catch (Throwable t) {
			if (BuildConfig.DEBUG)
				t.printStackTrace();
			throw new RuntimeException();
		}

		switchJSONToObject(json, obj);

		return obj;

	}

	private List<T> parser(JSONArray jsonArray) {

		List<T> dataList = new ArrayList<T>();

		if (jsonArray != null) {
			// 遍历数组
			JSONObject jsonObj = null;
			T data = null;

			for (int index = 0, count = jsonArray.length(); index < count; index++) {

				jsonObj = null;
				data = null;

				try {
					jsonObj = jsonArray.getJSONObject(index);
					data = (T) dataClass.newInstance();
					switchJSONToObject(jsonObj, data);
				} catch (Throwable t) {
					if (BuildConfig.DEBUG)
						Log.d(TAG, new StringBuilder("error on retrive ").append(this.getClass().getSimpleName()).append("from").append(jsonObj).toString());
					t.printStackTrace();

				}
				if (data != null)
					dataList.add(data);

			}
		}
		return dataList;
	}

	/**
	 * 反射，实现的是数据源和实体类之间的连接，减少代码釄1�7
	 */
	@SuppressWarnings("unchecked")
	public void switchJSONToObject(JSONObject jsonObj, Object obj) {

		StringBuilder logMessage = new StringBuilder();
		Iterator<String> keys = jsonObj.keys();
		Class<?> c = obj.getClass();
		String key;
		Method m;
		Field f = null;

		while (keys.hasNext()) {

			key = keys.next();

			try {

				// find field
				try {
					f = c.getDeclaredField(key);
				} catch (NoSuchFieldException e) {
					if (BuildConfig.DEBUG) {
						Log.d(TAG, "解析对象中，JSON 字符串中的 ‘" + key + "’ 未在 ‘" + c + "’ 中找到相应字段，略过。");
					}
					continue;
				}
				f.setAccessible(true);
				/**
				 * for our sake f embed another josonarray simple do it as String.
				 */

				Object value = jsonObj.opt(key);
				if (value == null)
					continue;

				if (value instanceof JSONArray) {// 以[]包起来的json数据

					if (f.getType().equals(List.class)) {
						// list recursion to ge value
						// f.set(obj, jsonObj.get(key).toString());
						// get the element type
						Type type = f.getGenericType();
						if (type instanceof ParameterizedType) {
							ParameterizedType pt = (ParameterizedType) type;
							Type returnType = pt.getActualTypeArguments()[0];
							Class<?> nestClass = null;
							try {
								String nestClassString = ((TypeVariable) returnType).getName();
								if ("T".equals(nestClassString))
									nestClass = dataClass;
							} catch (Throwable t) {

							}
							if (nestClass == null)
								nestClass = Class.forName(((Class) returnType).getName());

							JSONParser innerParser = new JSONParser(nestClass);
							f.set(obj, innerParser.parser((JSONArray) value));
						}
					} else {

						// default do it as string
						f.set(obj, value.toString());
					}

				} else if (value instanceof JSONObject) { // 以{}包起来的数据。

					Object newInstance = f.getType().newInstance();
					switchJSONToObject((JSONObject) value, newInstance);
					f.set(obj, newInstance);

				} else {
					// simple set value
					f.set(obj, value);

				}

				f.setAccessible(false);

			} catch (Throwable t) {
				if (SEE_REFLECT) {
					t.printStackTrace();
					logMessage.setLength(0);
					try {
						if (BuildConfig.DEBUG)
							Log.e(TAG,
									logMessage.append(key).append("---").append(f == null ? "" : f.getName()).append(",type:")
											.append(jsonObj.get(key).getClass()).append(",message:").append(t.getMessage()).toString());
						t.printStackTrace();
					} catch (JSONException e) {

						e.printStackTrace();
					}
				}

			} finally {
				try {
					f.setAccessible(false);
				} catch (Throwable t) {
				}
			}

		}

	}
}

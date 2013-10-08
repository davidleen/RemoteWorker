package com.eu.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.net.Uri;
import android.util.Log;

import com.example.remotedataworker.BuildConfig;

/**
 * 远程读取json 数据籄1�7 定制 action 动�1�7�添加参敄1�7 执行read;
 * 
 * @author davidleen29
 * @param <T> 返回数据类型
 */

public class AndroidClient<T> {
	private static StringBuilder errorMessage = new StringBuilder();
	protected String TAG;
	protected String action;
	private StringBuilder params;
	private Map<String, Object> dynamicParams;

	private Class<?> dataClass;
	private Class<?> resultClass;
	private File file;

	private List<NameValuePair> nameValuePairs;

	public AndroidClient(String action, Class<?> resultClass, Class<?> dataClass) {
		TAG = this.getClass().getSimpleName();

		this.action = action;
		this.resultClass = resultClass;
		this.dataClass = dataClass;

	}

	/**
	 * 添加参数
	 * 
	 * @param key 参数字段
	 * @param value 参数倄1�7
	 */
	public void addParams(String key, Object value) {

		if (params == null)
			params = new StringBuilder();
		params.append("&").append(Uri.encode(key.trim())).append("=").append(Uri.encode(value == null ? "" : value.toString().trim()));

	}

	/**
	 * 添加动态参数 相同的key 新值会替换旧值
	 * 
	 * @param key
	 * @param value
	 */

	public void addDynamicParams(String key, Object value) {
		if (dynamicParams == null)
			dynamicParams = new HashMap<String, Object>();
		dynamicParams.put(key, value);

	}

	/**
	 * 添加批量参数 将会以entity 形式进行远程调用 适用大量参数，无法以url 拼接形式进行访问〄1�7
	 * 
	 * @param key
	 * @param value
	 */
	public void addPatchParams(String key, Object value) {

		if (nameValuePairs == null)
			nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(key.trim(), value == null ? "" : value.toString().trim()));

	}

	/**
	 * 添加文件 附件传送
	 * 
	 * @param file
	 */

	public void addFile(File file) {
		this.file = file;
	}

	/**
	 * 生成访问uri
	 * 
	 * @param context
	 * @return
	 */
	private String getUriiString() {

		StringBuilder sb = new StringBuilder();

		sb.append(getwWebServiceName());

		sb.append(getActString(action));

		sb.append(params == null ? "" : params);
		// 添加动�1�7�参敄1�7
		if (dynamicParams != null) {
			Set<String> keys = dynamicParams.keySet();
			Object value;
			for (String key : keys) {
				value = dynamicParams.get(key);
				sb.append("&").append(Uri.encode(key.trim())).append("=").append(Uri.encode(value == null ? "" : value.toString().trim()));
			}

		}

		// 如果是登录操佄1�7 不夹带额外的类似session参数

		sb.append(getExtraParams());
		if (BuildConfig.DEBUG)
			Log.d(TAG, "uriString:" + sb.toString());
		return sb.toString();
	}

	protected String getActString(String action) {
		return "act=" + action;
	}

	protected String getExtraParams() {

		return "";
	}

	protected String[] getBaseUri() {
		return new String[] { "http://91yong.com" };
	}

	protected String[] getRealIPs() {
		return new String[] { "192.168.0.199" };

	}

	protected String getEntryKey() {
		return "webEntryKey";
		// return context.getResources().getString(R.string.webEntryKey);
	}

	protected String getwWebServiceName() {
		return "webServiceName";
		// return context.getResources().getString(R.string.webServiceName);
	}

	/**
	 * 执行读取操作〄1�7
	 * 
	 * @return
	 * @throws Exception
	 */

	public T read() throws Exception {

		HttpEntity entity = null;
		if (nameValuePairs != null)

			entity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
		else if (file != null) {

			FileInputStream fi = new FileInputStream(file);
			// Log.e(TAG, "file exists:" + file.exists() + ",fi:" + fi.available());
			// fi.close();
			// fi = new FileInputStream(file);
			InputStreamBody1 isb = new InputStreamBody1(fi, file.getName());
			isb.setLength(file.length());
			MultipartEntity multipartEntity = new MultipartEntity();
			multipartEntity.addPart("file", isb);

			entity = multipartEntity;
			// entity = new MultipartEntity(file, "multipart/form-data");//  "multipart/form-data;
		}

		return getResponse(getUriiString(), entity);

	}

	/**
	 * 自定义streamBody 重载实现getContentLength
	 * 
	 * @author davidleen29
	 */
	public class InputStreamBody1 extends InputStreamBody {
		private long length;

		public InputStreamBody1(InputStream in, String filename) {
			super(in, filename);

		}

		@Override
		public long getContentLength() {

			return length;
		}

		public long getLength() {
			return length;
		}

		public void setLength(long length) {
			this.length = length;
		}
	}

	// /**
	// * 上传头像接口 特别处理
	// */
	// public T updateFile(File data) throws Exception {
	//
	// //
	// StringBuilder sb = new StringBuilder();
	// sb.append(getwWebServiceName(context));
	// sb.append("act=");
	// sb.append(action);
	// sb.append(getExtraParams(context));
	// HttpPost httppost = new HttpPost(sb.toString());
	//
	// FileEntity entity = new FileEntity(data, "binary/octet-stream");
	// httppost.setEntity(entity);
	// // byteEntity.setContentType("binary/octet-stream");
	//
	// return getResponse(sb.toString(), entity);
	//
	// }

	private T getResponse(String url, HttpEntity entity) throws ClientProtocolException, IOException, Exception {

		long start = Calendar.getInstance().getTimeInMillis();
		HttpResponse sp = NetJobUtil.doRetryNetExecute(getBaseUri(), getRealIPs(), url, entity);
		if (BuildConfig.DEBUG)
			Log.d(TAG, "timeUsed in read data:" + (Calendar.getInstance().getTimeInMillis() - start));
		int statusCode = sp.getStatusLine().getStatusCode();
		switch (statusCode) {

		case HttpStatus.SC_OK: // ok resolve data;
			JSONParser<T> dataParser = new JSONParser<T>(dataClass);
			String jsonResult = EntityUtils.toString(sp.getEntity());
			if (BuildConfig.DEBUG)
				Log.d(TAG, "jsonResult" + jsonResult);

			T data = (T) dataParser.parse(jsonResult, resultClass);
			if (BuildConfig.DEBUG)
				Log.d(TAG, "timeUsed in read + parse data:" + (Calendar.getInstance().getTimeInMillis() - start));

			return data;
		default: //
		{

			if (BuildConfig.DEBUG)
				Log.d(TAG, "statusCode:" + statusCode);
			throw new ConnectException("ERROR_CONNECT");
		}

		}
	}
}

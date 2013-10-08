package com.eu.remote.test;

import android.content.Context;
import android.util.Log;

import com.eu.remote.Algorithm;
import com.eu.remote.AndroidClient;
import com.eu.remote.test.ActionMetaData.Login;
import com.example.remotedataworker.BuildConfig;
import com.example.remotedataworker.R;

public class ExeClient<T> extends com.eu.remote.AndroidClient<RemoteData<T>> {

	public static final String KEY_LOGIN = "LOGIN_SID";

	public static final String ACT_LOGIN = ActionMetaData.Login.NAME;
	private Context context;

	public ExeClient(Context context, String action, Class<?> dataClass) {
		super(action, RemoteData.class, dataClass);
		this.context = context;
	}

	@Override
	public RemoteData<T> read() {

		RemoteData<T> remoteData;
		try {
			remoteData = super.read();
		} catch (Exception e) {
			e.printStackTrace();
			remoteData = new RemoteData<T>();
			remoteData.flag = RemoteData.FLAG_ERROR_CONNECT;

		}
		if (BuildConfig.DEBUG)
			Log.d(TAG, "remoteData:" + remoteData);

		saveExtraMessage(remoteData);

		return remoteData;

	}

	private void saveExtraMessage(RemoteData<T> remoteData) {
		if (remoteData.isSuccess() && ACT_LOGIN.equals(remoteData.act)) {

			// SharedPreferencesUtil.save(KEY_LOGIN, ((Data_LoginInfo) remoteData.data.get(0)).sid);

		}

	}

	/**
	 * 增加额外参数
	 * 
	 * @return
	 */
	@Override
	protected String getExtraParams() {
		if (ACT_LOGIN.equals(action)) {
			return "";
		}

		String seed = String.valueOf((int) (Math.random() * 10000 + 10001)).substring(1);
		String entry;
		String sid = "";
		// SharedPreferencesUtil.getString(KEY_LOGIN);
		if (sid != null && sid.length() > 0) {
			entry = Algorithm.getMD5Str(sid + seed + getEntryKey());
			return new StringBuilder("&loginCode=").append(sid).append("&seed=").append(seed).append("&entry=").append(entry).toString();
		}
		return "";
	}

	@Override
	protected String[] getBaseUri() {
		return context.getResources().getStringArray(R.array.com_eu_remote_zxl_urls);
	}

	@Override
	protected String[] getRealIPs() {
		return context.getResources().getStringArray(R.array.com_eu_remote_zxl_real_ip);
	}

	@Override
	protected String getEntryKey() {
		return context.getResources().getString(R.string.com_eu_remote_webEntryKey);
	}

	@Override
	protected String getwWebServiceName() {
		return context.getResources().getString(R.string.com_eu_remote_webServiceName);
	}

}

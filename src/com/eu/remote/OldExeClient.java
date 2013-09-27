package com.eu.remote;

import android.content.Context;

public class OldExeClient extends AndroidClient<OldRemoteData<Class<?>>> {
	private Context context;

	public OldExeClient(Context context, String action, Class<?> dataClass) {
		super(action, RemoteData.class, dataClass);
		this.context = context;
	}

	// /**
	// * 上传头像接口 特别处理
	// */
	// @Override
	// public OldRemoteData<Class<?>> updateFile(File data) throws Exception {
	//
	// OldRemoteData<Class<?>> result = super.updateFile(data);
	//
	// return result;
	// }

}

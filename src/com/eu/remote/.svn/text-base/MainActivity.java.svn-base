package com.eu.remote;

import android.app.Activity;
import android.os.Bundle;

import com.example.remotedataworker.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.com_eu_remote_activity_main);

		ExeClient<Data_LoginInfo> client = new ExeClient<Data_LoginInfo>(this, "login", Data_LoginInfo.class);
		client.addParams("userName", "138191@91eu");
		client.addParams("password", Algorithm.getMD5Str("1"));
		client.read();

		ExeClient<Data_Task> client2 = new ExeClient<Data_Task>(this, "inquireTask", Data_Task.class);
		client2.read();
	}
}

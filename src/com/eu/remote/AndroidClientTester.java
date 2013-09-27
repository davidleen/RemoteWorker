package com.eu.remote;

import org.junit.Test;

/**
 * 远程读取json 数据籄1�7 定制 action 动�1�7�添加参敄1�7 执行read;
 * 
 * @author davidleen29
 * @param <T> 返回数据类型
 */

public class AndroidClientTester {

	// http://192.168.19.41:8000//zxl/api/v2.0/login.json?&username=test002%40126.com&password=e10adc3949ba59abbe56e057f20f883e

	String[] hostNames = new String[] { "www.91yong.com" };
	String[] realIPs = new String[] {};
	String url = "/zxl/api/v2.0/login.json?&username=test002%40126.com&password=e10adc3949ba59abbe56e057f20f883e";

	@Test
	public void testRead() {

		AndroidClient<Void> client = new AndroidClient<Void>("login", RemoteData.class, Void.class);
		client.addParams("username", "test002@126.com");
		client.addParams("password", "e10adc3949ba59abbe56e057f20f883e");
		try {
			client.read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

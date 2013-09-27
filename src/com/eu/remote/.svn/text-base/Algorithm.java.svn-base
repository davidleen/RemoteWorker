package com.eu.remote;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 算法类
 * 
 * @author Administrator
 */
public class Algorithm {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/*
	 * MD5加密
	 */
	public static String getMD5Str(String toMD5String) {

		String md5String = null;
		if (toMD5String != null) {
			try {
				// 创建具有指定算法名称的信息摘
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行后更新，然后完成摘要计算
				byte[] results = md.digest(toMD5String.getBytes());
				// 将得到的字节数组变成字符串返
				return byteArrayToHexString(results);
			} catch (NoSuchAlgorithmException message) {
				message = new NoSuchAlgorithmException("密码加密失败，操作出现异常");

			}
		}
		return md5String;

	}

	private static String byteArrayToHexString(byte[] byteToHex) {
		StringBuilder resultSb = new StringBuilder();
		for (byte bt : byteToHex) {
			resultSb.append(byteToHexString(bt));
		}
		return resultSb.toString();
	}

	// 将一个字节转化成十六进制形式的字符串
	private static String byteToHexString(byte bt) {
		int nbyte = bt;
		if (nbyte < 0)
			nbyte = 256 + nbyte;
		int d1 = nbyte / 16;
		int d2 = nbyte % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

}

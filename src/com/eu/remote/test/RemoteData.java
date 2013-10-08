package com.eu.remote.test;

import java.util.List;

/**
 * 远程数据类
 * 
 * @author Administrator
 * @param <T> 包含的实体数据类型<
 */
public class RemoteData<T> {

	private static final String TAG = "RemoteData<T>";

	/**
	 * 标识
	 */
	public String flag;

	/**
	 * 额外信息 经常不存在
	 */
	public String info;
	/**
	 * 查询的名称。
	 */
	public String act;

	/**
	 * 当前页数
	 */
	public int pageIndex;

	/**
	 * 每页大小
	 */
	public int pageRows;

	/**
	 * 总行数
	 */
	public int totalRows;

	/**
	 * 数据字段域
	 */
	public List<T> data;

	public RemoteData() {

	}

	/**
	 * 判读返回结果是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return FLAG_SUCCESS.equals(flag);
	}

	/**
	 * 是否没有出现错误 无数据算没有错误 =OK
	 * 
	 * @return
	 */
	public boolean isOK() {

		return FLAG_SUCCESS.equals(flag) || FLAG_NO_DATA.equals(flag);

	}

	// 远程返回的各个字段
	private static final String FLAG = "flag";
	private static final String PAGEINDEX = "pageIndex";
	private static final String PAGEROWS = "pageRows";
	private static final String TOTALROWS = "totalRows";
	private static final String PAGENUM = "pageNum";
	private static final String MESSAGE = "info";
	private static final String ACT = "act";
	private static final String DATA = "data";

	/**
	 * 远程数据异常标识定义
	 */
	public static final String FLAG_ERROR_CONNECT = "ERROR_CONNECT";
	public static final String FLAG_ERROR_RESOLVEJSON = "ERROR_RESOLVEJSON";
	public static final String FLAG_ERROR_RETRIEVE_FLAG = "ERROR_RETRIEVE_FLAG";
	public static final String FLAG_ERROR_TIMEOUT = "FLAG_ERROR_TIMEOUT";

	public static final String FLAG_ENTITY_NOFOND = "ENTITY_NOT_FOUND";

	// 远程访问操作状态标识字段

	public static final String FLAG_SUCCESS = "SUCCESS";
	public static final String FLAG_DENIED = "DENIED";
	public static final String FLAG_FAILURE_USER_PASSWORD_ERROR = "FAILURE_USER_PASSWORD_ERROR";
	public static final String FLAG_FAILURE_NO_USER = "FAILURE_NO_USER";
	public static final String FLAG_NO_DATA = "NO_DATA";
	public static final String FLAG_UNLOGIN = "UNLOGIN";
	public static final String operateFlg = "operateFlg";
	public static final String operateFlg_SF = "SF";
	public static final String operateFlg_SS = "SS";
	public static final String operateFlg_OS = "OS";
	public static final String operateFlg_OF = "OF";
	public static final String FLAG_PLANTIME_IS_ERROR = "PLANTIME_IS_ERROR"; //
	public static final String FLAG_EXCEPTION = "EXCEPTION";
	public static final String CUSTOM_EXCEPTION = "CUSTOM_EXCEPTION";

	public static final String FLAG_INVALID = "INVALID";

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RemoteData [flag=").append(flag).append(", info=").append(info).append(", act=").append(act).append(", pageIndex=").append(pageIndex)
				.append(", pageRows=").append(pageRows).append(", totalRows=").append(totalRows).append(", data=").append(data).append("]");
		return builder.toString();
	}

}

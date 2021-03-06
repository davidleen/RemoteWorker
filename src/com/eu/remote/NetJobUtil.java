package com.eu.remote;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

import com.example.remotedataworker.BuildConfig;

public class NetJobUtil {

	private final static int DEFAULTTIMEOUT = 120000;
	// 容错处理 重新远程调用的最大次数
	private static final int RETRY_TIME = 3;
	private static final String TAG = "NetJobUtil";
	private static final long MAXTIMEFORADDRESS = 2000l;

	private static AbstractHttpClient defaultClient = null;

	// 远程调用允许最大时长

	/**
	 * @param hostNames can be more than 1 hostName for the your url, hostaddress may be null to use an absolute url
	 * @param realIPs realIp for hostNameUnsuppport error
	 * @param url a relative url or a absolute url
	 * @param httpEntity
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public static HttpResponse doRetryNetExecute(String[] hostNames, String[] realIPs, String url, HttpEntity httpEntity) throws ClientProtocolException,
			IOException, Exception {

		return doRetryNetExecute(hostNames, realIPs, url, httpEntity, true);
	}

	public static HttpResponse doRetryNetExecute(String[] hostNames, String[] realIPs, String url, HttpEntity httpEntity, boolean needRetry)
			throws ClientProtocolException, IOException, Exception {

		AbstractHttpClient client = getHttpClient();
		String completeUrl;
		if (hostNames != null && hostNames.length > 0) {

			completeUrl = new StringBuilder("http://").append(hostNames[0]).append(url).toString();
		} else {
			// if no host means this url is a absolute url , so use it directly
			completeUrl = url;
		}
		if (BuildConfig.DEBUG) {
			Log.d(TAG, new StringBuilder("completeUrl:").append(completeUrl).toString());
			Log.d(TAG, new StringBuilder("httpEntity:").append(httpEntity).toString());
		}
		HttpPost post = new HttpPost(completeUrl);
		if (httpEntity != null)
			post.setEntity(httpEntity);

		HttpResponse result = null;
		long startTime = Calendar.getInstance().getTimeInMillis();
		int retryTime = 0;

		do {

			if (BuildConfig.DEBUG)
				Log.e(TAG, new StringBuilder("retryTime on remote invoke:").append(retryTime).toString());
			// Log.e("TAG", "timeUsed:"
			// + (Calendar.getInstance().getTimeInMillis() - startTime));
			Exception error = null;
			try {
				if (result != null) {
					// 如果前次请求的结果不为空，则 清空内容。否则 再次请求会卡住
					result.getEntity().consumeContent();
				}
				result = client.execute(post);

				int statueCode = result.getStatusLine().getStatusCode();
				if (statueCode == HttpStatus.SC_OK) {
					break;
				}

				if (BuildConfig.DEBUG)
					Log.e(TAG, "statueCode:" + statueCode);

			} catch (UnknownHostException e) {
				error = e;
				if (BuildConfig.DEBUG)
					Log.e(TAG, new StringBuilder("请求失败  --UnknownHostException:").append(e.getMessage()).toString());

				// 如果有传递真实ip进来，在域名解析失败时候 启动真实ip替代 进行尝试。
				if (realIPs != null && realIPs.length > 0) {
					completeUrl = String.format("http://%s%s", realIPs[0], url);
					if (BuildConfig.DEBUG)
						Log.e(TAG, new StringBuilder("使用内置真实ip替代 --").append(realIPs[0]).append("===url:").append(completeUrl).toString());
					post.setURI(new URI(completeUrl));
				}

			} catch (ClientProtocolException e) {

				error = e;

			} catch (IOException e) {
				error = e;

			} catch (Throwable t) {

				if (BuildConfig.DEBUG) {
					Log.e(TAG, new StringBuilder("Throwable:").append(t.getLocalizedMessage()).toString());

					t.printStackTrace();

				}
			}

			if (error != null) {
				if (BuildConfig.DEBUG) {
					Log.e(TAG, new StringBuilder("error:").append(error).toString());
					error.printStackTrace();
				}

			}
			// 是否时间夠用。
			boolean hasEnoughTime = (Calendar.getInstance().getTimeInMillis() - startTime) < DEFAULTTIMEOUT;
			if (retryTime < RETRY_TIME && hasEnoughTime) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
				}

			} else {
				if (error != null) {

					if (error instanceof ClientProtocolException)
						throw (ClientProtocolException) error;
					if (error instanceof IOException)
						throw (IOException) error;
					throw error;

				}
				break;

			}

			retryTime++;
		} while (needRetry);

		return result;

	}

	/**
	 * 获取HttpClient
	 * 
	 * @return
	 */
	private synchronized static AbstractHttpClient getHttpClient() {

		// 如果是demo 提示功能关闭

		if (defaultClient == null) {
			// Log.v(TAG, "defaultClient:" + "create");
			SchemeRegistry schemeRegistry = new SchemeRegistry();

			// http scheme
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			// https scheme
			schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

			HttpParams params = new BasicHttpParams();
			params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 1);
			params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(1));
			params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULTTIMEOUT);
			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, DEFAULTTIMEOUT);
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

			ThreadSafeClientConnManager clientConnectionManager = new ThreadSafeClientConnManager(params, schemeRegistry);
			//
			HttpContext context = new BasicHttpContext();
			context.setAttribute(ClientContext.CREDS_PROVIDER, credentialsProvider);
			// AbstractHttpClient client = new DefaultHttpClient();
			// client.setParams(params);

			defaultClient = new DefaultHttpClient(clientConnectionManager, params);
		}

		return defaultClient;

		// return client;
	}

	/**
	 * 获取合适的ip 容错处理
	 * 
	 * @param hostNames 域名数组
	 * @param realIps 真实ip数组 备用
	 * @deprecated 不要依赖于解析域名得到的真实ip ip不一定是真实的地址。 即使是真实的地址，可能挂靠几个应用，不一定是你想访问的应用。
	 */
	private static String fetchSuitableIPorHostName(String[] hostNames, String[] realIPs) {

		String ip = null;
		int retryAddressTime = 0;
		long startTime = Calendar.getInstance().getTimeInMillis();
		for (String hostName : hostNames) {
			retryAddressTime = 0;
			do {
				if (BuildConfig.DEBUG)
					Log.e(TAG, new StringBuilder("look up hostByNameByName :").append(retryAddressTime).append(" on host:").append(hostName).toString());

				try {
					InetAddress address = InetAddress.getByName(hostName);

					ip = address.getHostAddress();
					if (BuildConfig.DEBUG)
						Log.e(TAG, new StringBuilder("resolve ").append(hostName).append("  success!:").append(ip).append(",time used in fetch real id:")
								.append(Calendar.getInstance().getTimeInMillis() - startTime).toString());
					break;

				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(100);
				} catch (Throwable t) {
				}

				long timeUsed = Calendar.getInstance().getTimeInMillis() - startTime;
				// if(address time to long ,cut this loop;
				if (timeUsed > MAXTIMEFORADDRESS) {
					break;
				}
				retryAddressTime++;

			} while (retryAddressTime < RETRY_TIME);
			// if find id end loop immediately
			if (ip != null)
				break;
		}
		// no found real IP
		if (ip == null) {

			// use second ip
			if (realIPs != null && realIPs.length > 0) {
				// for shortcut we use first ip , may extend in the future
				// and ping the ip
				ip = realIPs[0];
			}
			if (ip != null)
				if (BuildConfig.DEBUG)
					Log.e(TAG, new StringBuilder("use the real ip  define in the mainfests :").append(ip).toString());
		}
		// if ip still nofound
		if (ip == null) {
			// use default first hostName
			ip = hostNames[0];
		}

		return ip;

	}

}

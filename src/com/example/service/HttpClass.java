package com.example.service;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.example.logtool.LogTools;

import android.content.Context;
import android.util.Log;

public class HttpClass {

	public static Context context;
	private static final String TIME_OUT = "���ӳ�ʱ�����Ժ�����";
	private static final String QRY_FAIL = "��ѯʧ��";
	static String TAG = "";
	static String GBK = "gbk";

	/* ��ȡhttpClient����ʱʱ��20s */
	private static DefaultHttpClient getDefaultHttpClient() {
		DefaultHttpClient client;
		HttpParams httpParams = new BasicHttpParams();
		// ���ô���
		String host = android.net.Proxy.getDefaultHost();

		int port = android.net.Proxy.getDefaultPort();
		Log.v(TAG, "����" + host + "���˿ڣ�" + port + "\n---host" + host);
		if (host != null) {
			HttpHost httpHost = new HttpHost(host, port);
			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHost);
		}
		// ���ó�ʱ
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
		HttpConnectionParams.setSoTimeout(httpParams, 20000);

		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));

		// ʹ���̰߳�ȫ�����ӹ���������HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				httpParams, schReg);
		client = new DefaultHttpClient(conMgr, httpParams);

		return client;
	}

	public static String getWeather() {

		String result = null;
		String city = "����";// 101280601����
		String url = "http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&day=2&city="
				+ city + "&dfc=3";
		try {
			DefaultHttpClient httpClient = getDefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity(), GBK);

				LogTools.print(result);// ��ӡ�����˻�ȡ����������
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
}

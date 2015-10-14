package com.example.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.logtool.LogTools;

public class NewThread implements Runnable {

	// ͨ�������ͷ�������ApplicationContext()�����⻹����ͨ�����캯����������
	private Context context = null;

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClass httpClass = new HttpClass();

		// LogTools.print(httpClass.getWeather());
		// LogTools.print("---Ϊʲôû�����");
		// Log.i("NewThread",
		// "httpClass.getWeather()������û�ж�������" + httpClass.getWeather());

		// �˴����͹㲥����activity����չ㲥��������handler�ش���Activity

		Intent intent = new Intent();
		intent.setAction("adnroid.intent.action.WEATHER_RECEVIE");
		intent.putExtra("weatherinfo", httpClass.getWeather().toString());
		context.sendBroadcast(intent);/*
									 * sendBroadcast()��activity���õ�ʱ���ô�context
									 * ��service��Ҳһ��
									 * �����ڱ�ĵط�ʹ��ʱҪ����ApplicationContext()
									 * ����ʱ�ſ��Ե���sendBroadcast()
									 */
	}

}

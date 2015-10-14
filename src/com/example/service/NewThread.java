package com.example.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.logtool.LogTools;

public class NewThread implements Runnable {

	// 通过变量和方法传递ApplicationContext()，此外还可以通过构造函数传递数据
	private Context context = null;

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClass httpClass = new HttpClass();

		// LogTools.print(httpClass.getWeather());
		// LogTools.print("---为什么没有输出");
		// Log.i("NewThread",
		// "httpClass.getWeather()到底有没有东西回来" + httpClass.getWeather());

		// 此处发送广播（在activity里接收广播），或者handler回传给Activity

		Intent intent = new Intent();
		intent.setAction("adnroid.intent.action.WEATHER_RECEVIE");
		intent.putExtra("weatherinfo", httpClass.getWeather().toString());
		context.sendBroadcast(intent);/*
									 * sendBroadcast()在activity里用的时候不用传context
									 * 在service里也一样
									 * ，当在别的地方使用时要传入ApplicationContext()
									 * ，这时才可以调用sendBroadcast()
									 */
	}

}

package com.example.service;

import com.example.logtool.LogTools;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BindService extends Service {

	private String TAG = "BackGroundService";
	private MyBinder binder = new MyBinder();

	public class MyBinder extends Binder {

		/* 返回service服务，方便activity中得到 */
		public BindService getService() {
			return BindService.this;
		}
	}

	public void print() {
		LogTools.print("-------服务已经开启-------");
		NewThread nt = new NewThread();
		Thread thread = new Thread(nt);
		nt.setContext(getApplicationContext());
		thread.start();
		// LogTools.print("-------不知道为什么新开的线程没有打印东西出来-------");

	}

	/* 绑定时执行 */
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	// /* 只在创建时执行一次 */
	// @Override
	// public void onCreate() {
	// // TODO Auto-generated method stub
	// super.onCreate();
	// }
	//
	// /* 断开绑定或者stopService时执行 */
	// @Override
	// public void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// }
	//
	// /* 当内存不够时执行改方法 */
	// @Override
	// public void onLowMemory() {
	// // TODO Auto-generated method stub
	// super.onLowMemory();
	// }
	//
	// /* 当重新尝试绑定时执行 */
	// @Override
	// public void onRebind(Intent intent) {
	// // TODO Auto-generated method stub
	// super.onRebind(intent);
	// }
	//
	// /* ，每次startService都会执行该方法，而改方法执行后会自动执行onStart()方法 */
	// @Override
	// public int onStartCommand(Intent intent, int flags, int startId) {
	// // TODO Auto-generated method stub
	// return super.onStartCommand(intent, flags, startId);
	// }
	//
	// /* 断开绑定时执行 */
	// @Override
	// public boolean onUnbind(Intent intent) {
	// // TODO Auto-generated method stub
	// return super.onUnbind(intent);
	// }

}

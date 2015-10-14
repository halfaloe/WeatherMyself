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

		/* ����service���񣬷���activity�еõ� */
		public BindService getService() {
			return BindService.this;
		}
	}

	public void print() {
		LogTools.print("-------�����Ѿ�����-------");
		NewThread nt = new NewThread();
		Thread thread = new Thread(nt);
		nt.setContext(getApplicationContext());
		thread.start();
		// LogTools.print("-------��֪��Ϊʲô�¿����߳�û�д�ӡ��������-------");

	}

	/* ��ʱִ�� */
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	// /* ֻ�ڴ���ʱִ��һ�� */
	// @Override
	// public void onCreate() {
	// // TODO Auto-generated method stub
	// super.onCreate();
	// }
	//
	// /* �Ͽ��󶨻���stopServiceʱִ�� */
	// @Override
	// public void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// }
	//
	// /* ���ڴ治��ʱִ�иķ��� */
	// @Override
	// public void onLowMemory() {
	// // TODO Auto-generated method stub
	// super.onLowMemory();
	// }
	//
	// /* �����³��԰�ʱִ�� */
	// @Override
	// public void onRebind(Intent intent) {
	// // TODO Auto-generated method stub
	// super.onRebind(intent);
	// }
	//
	// /* ��ÿ��startService����ִ�и÷��������ķ���ִ�к���Զ�ִ��onStart()���� */
	// @Override
	// public int onStartCommand(Intent intent, int flags, int startId) {
	// // TODO Auto-generated method stub
	// return super.onStartCommand(intent, flags, startId);
	// }
	//
	// /* �Ͽ���ʱִ�� */
	// @Override
	// public boolean onUnbind(Intent intent) {
	// // TODO Auto-generated method stub
	// return super.onUnbind(intent);
	// }

}

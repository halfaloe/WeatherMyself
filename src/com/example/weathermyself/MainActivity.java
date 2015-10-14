package com.example.weathermyself;

import com.example.logtool.LogTools;
import com.example.service.BindService;
import com.example.service.BindService.MyBinder;
import com.example.service.HttpClass;
import com.example.weathermyself.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// private Button btn;
	private TextView tv_city, tv_today, temperature, weather_situation,
			wind_direction, tv_noresult;// temperature气温，weather_situation天气，wind_direction风向
	private TextView tv_date1, tv_date2, tv_wd1, tv_wd2;// tv_date1明天，tv_date2后天
	private LinearLayout ll_yes, ll_no;// 布局
	private ImageView ima, ima1, ima2;// 设置要显示天气情况的imageview
	// private BackGroundService bService = null;
	private boolean isConnected = false;
	private String weatherString = null;

	private WeatherReceiver receiver;// 自定义广播接收者
	private IntentFilter filter;// intent过滤器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();// 初始化控件
		// QueryAndShow asyncTask = new QueryAndShow();
		// asyncTask.execute("");
		receiver = new WeatherReceiver();
		filter = new IntentFilter();
		filter.addAction("adnroid.intent.action.WEATHER_RECEVIE");
		resolveWeather();
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			isConnected = false;
		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder binder) {
			// TODO Auto-generated method stub
			MyBinder myBinder = (MyBinder) binder;
			BindService bService = myBinder.getService();
			bService.print();
			isConnected = true;
			Log.i("onServiceConnected", "bService是：" + bService);
		}
	};

	private void initView() {
		ll_yes = (LinearLayout) this.findViewById(R.id.ws2_ll_yes);
		ll_no = (LinearLayout) this.findViewById(R.id.ws2_ll_no);

		tv_city = (TextView) this.findViewById(R.id.ws2_tv_city);
		ima = (ImageView) this.findViewById(R.id.ws2_iv_image);
		temperature = (TextView) this.findViewById(R.id.ws2_tv_attr1);
		weather_situation = (TextView) this.findViewById(R.id.ws2_tv_attr2);
		wind_direction = (TextView) this.findViewById(R.id.ws2_tv_attr3);

		tv_noresult = (TextView) this.findViewById(R.id.ws2_tv_noresult);

		tv_date1 = (TextView) this.findViewById(R.id.ws2_tv_1_date);
		tv_date2 = (TextView) this.findViewById(R.id.ws2_tv_2_date);
		tv_wd1 = (TextView) this.findViewById(R.id.ws2_tv_1_wd);
		tv_wd2 = (TextView) this.findViewById(R.id.ws2_tv_2_wd);

		ima1 = (ImageView) this.findViewById(R.id.ws2_iv_1_image);
		ima2 = (ImageView) this.findViewById(R.id.ws2_iv_2_image);
		// btn = (Button) findViewById(R.id.btn);
		// btn.setOnClickListener(listener);

	}

	public OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			bind();
			Log.i("onClick", "绑定服务");

		}
	};

	// 自定义广播接收器
	public class WeatherReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// Log.i("onReceive", "getAction:" + intent.getAction());
			if (intent.getAction().equals(
					"adnroid.intent.action.WEATHER_RECEVIE")) {

				weatherString = intent.getStringExtra("weatherinfo");

				// LogTools.print(string);
				Toast.makeText(MainActivity.this, "" + weatherString,
						Toast.LENGTH_LONG).show();
			}
		}

	}

	// private class QueryAndShow extends AsyncTask {
	//
	// @Override
	// protected void onPostExecute(Object result) {
	// // 相当于Handler 处理UI的方式，在这里面可以使用在doInBackground 得到的结果处理操作UI
	// if (result != null) {
	// String weatherResult = (String) result;
	// if (weatherResult.split(";").length > 1) {
	// Log.i("weatherResult", "weatherResult:" + weatherResult);
	// String a = weatherResult.split(";")[1];
	// if (a.split("=").length > 1) {
	// String b = a.split("=")[1];
	// String c = b.substring(1, b.length() - 1);
	// String[] resultArr = c.split("\\}");
	// if (resultArr.length > 0) {
	// todayParse(resultArr[0]);
	// tommrowParse(resultArr[1]);
	// thirddayParse(resultArr[2]);
	// ll_yes.setVisibility(View.VISIBLE);
	// }
	//
	// } else {
	// DataUtil.Alert(MainActivity.this, "查无天气信息");
	// }
	// } else {
	// DataUtil.Alert(MainActivity.this, "查无天气信息");
	// }
	// } else {
	// DataUtil.Alert(MainActivity.this, "查无天气信息");
	// }
	// super.onPostExecute(result);
	// }
	//
	// @Override
	// protected Object doInBackground(Object... params) {
	//
	// /*
	// * 后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。
	// * 在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
	// */
	// LogTools.print("我随便打印点东西看看");
	//
	// // 返回得到的天气
	// return HttpClass.getWeather();
	// }
	//
	// }

	public void resolveWeather() {
		String result = (String) weatherString;
		Log.i("result", "result:---" + result);
		if (result != null) {
			String weatherResult = (String) result;
			if (weatherResult.split(";").length > 1) {
				Log.i("weatherResult", "weatherResult:" + weatherResult);
				String a = weatherResult.split(";")[1];
				if (a.split("=").length > 1) {
					String b = a.split("=")[1];
					String c = b.substring(1, b.length() - 1);
					String[] resultArr = c.split("\\}");
					if (resultArr.length > 0) {
						todayParse(resultArr[0]);
						tommrowParse(resultArr[1]);
						thirddayParse(resultArr[2]);
						ll_yes.setVisibility(View.VISIBLE);
					}

				} else {
					DataUtil.Alert(MainActivity.this, "查无天气信息");
				}
			} else {
				DataUtil.Alert(MainActivity.this, "查无天气信息");
			}
		} else {
			DataUtil.Alert(MainActivity.this, "查无天气信息");
		}

	}

	private void todayParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd = "";// 气温
		String tq = "";// 天气情况
		String fx = "";// 风向
		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "℃";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "℃";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}

			temperature.setText("气温：" + wd);
			weather_situation.setText("天气情况：" + tq);
			wind_direction.setText("风向：" + fx);
			ima.setImageResource(imageResoId(tq));

		}
	}

	private void tommrowParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd = "";// 温度
		String tq = "";// 天气
		String fx = "";// 风向
		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "℃";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "℃";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}
			tv_date1.setText("明天    " + tq);
			tv_wd1.setText(wd);
			ima1.setImageResource(imageResoId(tq));

		}
	}

	private void thirddayParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd = "";// 温度
		String tq = "";// 天气
		String fx = "";// 风向
		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "℃";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "℃";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}

			tv_date2.setText("后天    " + tq);
			tv_wd2.setText(wd);
			ima2.setImageResource(imageResoId(tq));

		}
	}

	private int imageResoId(String weather) {
		int resoid = R.drawable.s_2;
		if (weather.indexOf("多云") != -1 || weather.indexOf("晴") != -1) {// 多云转晴，以下类同
																		// indexOf:包含字串
			resoid = R.drawable.s_1;
		} else if (weather.indexOf("多云") != -1 && weather.indexOf("阴") != -1) {
			resoid = R.drawable.s_2;
		} else if (weather.indexOf("阴") != -1 && weather.indexOf("雨") != -1) {
			resoid = R.drawable.s_3;
		} else if (weather.indexOf("晴") != -1 && weather.indexOf("雨") != -1) {
			resoid = R.drawable.s_12;
		} else if (weather.indexOf("晴") != -1 && weather.indexOf("雾") != -1) {
			resoid = R.drawable.s_12;
		} else if (weather.indexOf("晴") != -1) {
			resoid = R.drawable.s_13;
		} else if (weather.indexOf("多云") != -1) {
			resoid = R.drawable.s_2;
		} else if (weather.indexOf("阵雨") != -1) {
			resoid = R.drawable.s_3;
		} else if (weather.indexOf("小雨") != -1) {
			resoid = R.drawable.s_3;
		} else if (weather.indexOf("中雨") != -1) {
			resoid = R.drawable.s_4;
		} else if (weather.indexOf("大雨") != -1) {
			resoid = R.drawable.s_5;
		} else if (weather.indexOf("暴雨") != -1) {
			resoid = R.drawable.s_5;
		} else if (weather.indexOf("冰雹") != -1) {
			resoid = R.drawable.s_6;
		} else if (weather.indexOf("雷阵雨") != -1) {
			resoid = R.drawable.s_7;
		} else if (weather.indexOf("小雪") != -1) {
			resoid = R.drawable.s_8;
		} else if (weather.indexOf("中雪") != -1) {
			resoid = R.drawable.s_9;
		} else if (weather.indexOf("大雪") != -1) {
			resoid = R.drawable.s_10;
		} else if (weather.indexOf("暴雪") != -1) {
			resoid = R.drawable.s_10;
		} else if (weather.indexOf("扬沙") != -1) {
			resoid = R.drawable.s_11;
		} else if (weather.indexOf("沙尘") != -1) {
			resoid = R.drawable.s_11;
		} else if (weather.indexOf("雾") != -1) {
			resoid = R.drawable.s_12;
		}
		return resoid;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, 1, 1, "刷新");
		menu.add(0, 2, 1, "服务");
		menu.add(0, 3, 1, "解绑");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			// QueryAndShow asyncTask = new QueryAndShow();
			// asyncTask.execute("");
			bind();
			resolveWeather();
			resolveWeather();
			Toast.makeText(MainActivity.this, "已经刷新", Toast.LENGTH_LONG).show();

			break;

		case 2:
			bind();
			Log.i("a", "绑定服务");
			break;
		case 3:
			if (isConnected == true) {
				unbind();
				Log.i("b", "解绑服务");
			}
			break;
		default:
			break;
		}
		return false;

	}

	// 绑定服务及解绑服务
	private void bind() {
		Intent intent = new Intent(MainActivity.this, BindService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private void unbind() {
		unbindService(conn);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		registerReceiver(receiver, filter);
		Log.i("", "广播已经注册");

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unbind();
		unregisterReceiver(receiver);
		Log.i("onStop", "广播已经注销");
	}

}

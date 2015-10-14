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
			wind_direction, tv_noresult;// temperature���£�weather_situation������wind_direction����
	private TextView tv_date1, tv_date2, tv_wd1, tv_wd2;// tv_date1���죬tv_date2����
	private LinearLayout ll_yes, ll_no;// ����
	private ImageView ima, ima1, ima2;// ����Ҫ��ʾ���������imageview
	// private BackGroundService bService = null;
	private boolean isConnected = false;
	private String weatherString = null;

	private WeatherReceiver receiver;// �Զ���㲥������
	private IntentFilter filter;// intent������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();// ��ʼ���ؼ�
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
			Log.i("onServiceConnected", "bService�ǣ�" + bService);
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
			Log.i("onClick", "�󶨷���");

		}
	};

	// �Զ���㲥������
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
	// // �൱��Handler ����UI�ķ�ʽ�������������ʹ����doInBackground �õ��Ľ���������UI
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
	// DataUtil.Alert(MainActivity.this, "����������Ϣ");
	// }
	// } else {
	// DataUtil.Alert(MainActivity.this, "����������Ϣ");
	// }
	// } else {
	// DataUtil.Alert(MainActivity.this, "����������Ϣ");
	// }
	// super.onPostExecute(result);
	// }
	//
	// @Override
	// protected Object doInBackground(Object... params) {
	//
	// /*
	// * ��ִ̨�У��ȽϺ�ʱ�Ĳ��������Է������ע�����ﲻ��ֱ�Ӳ���UI���˷����ں�̨�߳�ִ�У�����������Ҫ������ͨ����Ҫ�ϳ���ʱ�䡣
	// * ��ִ�й����п��Ե���publicProgress(Progress��)����������Ľ��ȡ�
	// */
	// LogTools.print("������ӡ�㶫������");
	//
	// // ���صõ�������
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
					DataUtil.Alert(MainActivity.this, "����������Ϣ");
				}
			} else {
				DataUtil.Alert(MainActivity.this, "����������Ϣ");
			}
		} else {
			DataUtil.Alert(MainActivity.this, "����������Ϣ");
		}

	}

	private void todayParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd = "";// ����
		String tq = "";// �������
		String fx = "";// ����
		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "��";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "��";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}

			temperature.setText("���£�" + wd);
			weather_situation.setText("���������" + tq);
			wind_direction.setText("����" + fx);
			ima.setImageResource(imageResoId(tq));

		}
	}

	private void tommrowParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd = "";// �¶�
		String tq = "";// ����
		String fx = "";// ����
		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "��";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "��";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}
			tv_date1.setText("����    " + tq);
			tv_wd1.setText(wd);
			ima1.setImageResource(imageResoId(tq));

		}
	}

	private void thirddayParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd = "";// �¶�
		String tq = "";// ����
		String fx = "";// ����
		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "��";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "��";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}

			tv_date2.setText("����    " + tq);
			tv_wd2.setText(wd);
			ima2.setImageResource(imageResoId(tq));

		}
	}

	private int imageResoId(String weather) {
		int resoid = R.drawable.s_2;
		if (weather.indexOf("����") != -1 || weather.indexOf("��") != -1) {// ����ת�磬������ͬ
																		// indexOf:�����ִ�
			resoid = R.drawable.s_1;
		} else if (weather.indexOf("����") != -1 && weather.indexOf("��") != -1) {
			resoid = R.drawable.s_2;
		} else if (weather.indexOf("��") != -1 && weather.indexOf("��") != -1) {
			resoid = R.drawable.s_3;
		} else if (weather.indexOf("��") != -1 && weather.indexOf("��") != -1) {
			resoid = R.drawable.s_12;
		} else if (weather.indexOf("��") != -1 && weather.indexOf("��") != -1) {
			resoid = R.drawable.s_12;
		} else if (weather.indexOf("��") != -1) {
			resoid = R.drawable.s_13;
		} else if (weather.indexOf("����") != -1) {
			resoid = R.drawable.s_2;
		} else if (weather.indexOf("����") != -1) {
			resoid = R.drawable.s_3;
		} else if (weather.indexOf("С��") != -1) {
			resoid = R.drawable.s_3;
		} else if (weather.indexOf("����") != -1) {
			resoid = R.drawable.s_4;
		} else if (weather.indexOf("����") != -1) {
			resoid = R.drawable.s_5;
		} else if (weather.indexOf("����") != -1) {
			resoid = R.drawable.s_5;
		} else if (weather.indexOf("����") != -1) {
			resoid = R.drawable.s_6;
		} else if (weather.indexOf("������") != -1) {
			resoid = R.drawable.s_7;
		} else if (weather.indexOf("Сѩ") != -1) {
			resoid = R.drawable.s_8;
		} else if (weather.indexOf("��ѩ") != -1) {
			resoid = R.drawable.s_9;
		} else if (weather.indexOf("��ѩ") != -1) {
			resoid = R.drawable.s_10;
		} else if (weather.indexOf("��ѩ") != -1) {
			resoid = R.drawable.s_10;
		} else if (weather.indexOf("��ɳ") != -1) {
			resoid = R.drawable.s_11;
		} else if (weather.indexOf("ɳ��") != -1) {
			resoid = R.drawable.s_11;
		} else if (weather.indexOf("��") != -1) {
			resoid = R.drawable.s_12;
		}
		return resoid;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, 1, 1, "ˢ��");
		menu.add(0, 2, 1, "����");
		menu.add(0, 3, 1, "���");
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
			Toast.makeText(MainActivity.this, "�Ѿ�ˢ��", Toast.LENGTH_LONG).show();

			break;

		case 2:
			bind();
			Log.i("a", "�󶨷���");
			break;
		case 3:
			if (isConnected == true) {
				unbind();
				Log.i("b", "������");
			}
			break;
		default:
			break;
		}
		return false;

	}

	// �󶨷��񼰽�����
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
		Log.i("", "�㲥�Ѿ�ע��");

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unbind();
		unregisterReceiver(receiver);
		Log.i("onStop", "�㲥�Ѿ�ע��");
	}

}

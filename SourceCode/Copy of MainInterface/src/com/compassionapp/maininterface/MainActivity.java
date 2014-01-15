package com.compassionapp.maininterface;



import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import com.compassion.maininterface.R;
import com.compassionapp.diary.AddNotes;
import com.compassionapp.diary.NotesList;
import com.compassionapp.generalSetting.optionsListview;
import com.compassionapp.meditation.MeditationSetting;
import com.compassionapp.meditation.countdownPage;
import com.compassionapp.notification.NotificationMain;
import com.compassionapp.notification.NotificationSetting;


import android.os.Bundle;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private static final String FILENAME="AppInfo";
	private static final String LOGINTIME="LoginTime";
	private ImageButton ibtnNotification=null;
	private ImageButton ibtnMeditation=null;
	private ImageButton ibtnDiary=null;
	private ImageButton ibtnSetting=null;
	private Calendar calendar;
	private String welcomeSignString="Welcome to the MCM Compassion App!";
	private int day;
	private long exitTime = 0; 
	SharedPreferences sharedPreference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 读取语言配置信息 使得应用能够自动加载之前的语�?���?
		Resources resource = getResources();
		Configuration config = resource.getConfiguration();
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题�?
		FirstTimeLogin("/data/data/com.compassion.maininterface/shared_prefs/LoginTime.xml");
		sharedPreference = super.getSharedPreferences(LOGINTIME, Activity.MODE_PRIVATE);//打开sharepreferenced file 查看之前保存的设�?
		if ("zh".equalsIgnoreCase(sharedPreference.getString("language", null)))//查看语言设置 
		{
			config.locale = Locale.SIMPLIFIED_CHINESE;
		} else if ("en".equalsIgnoreCase(sharedPreference.getString("language", null)))
		{
			config.locale = Locale.ENGLISH;
		} else
		{
			config.locale = Locale.getDefault();
		}
		getBaseContext().getResources().updateConfiguration(config, null);
		setContentView(R.layout.activity_main);
		ibtnNotification = (ImageButton) findViewById(R.id.imageButton1);
		ibtnMeditation = (ImageButton) findViewById(R.id.imageButton2);
		ibtnDiary = (ImageButton) findViewById(R.id.imageButton3);
		ibtnSetting = (ImageButton) findViewById(R.id.imageButton4);
		ibtnNotification.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				IsExist("/data/data/com.compassion.maininterface/shared_prefs/AppInfo.xml");
				// �?��是否存在共享数据文件�?如果不存在证明是第一次登�?那么就进而跳转到notification设置界面
			}
		});
		ibtnMeditation.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MeditationSetting.class);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
			}
		});
		ibtnDiary.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, NotesList.class);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
			}
		});
		ibtnSetting.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(MainActivity.this,optionsListview.class);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
			}
		});

	}
	public  void IsExist(String path){
		File file=new File(path);
		
		if (!file.exists())
		{
			createSharedPreFile();
			System.out.println("*********** AppInfo.xml "+file.exists()+"************");
			Intent intent=new Intent(MainActivity.this,NotificationSetting.class);
			MainActivity.this.startActivity(intent);
			MainActivity.this.finish();
		}
		else{
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, NotificationMain.class);
			MainActivity.this.startActivity(intent);
			MainActivity.this.finish();
		}
	}
	public  void FirstTimeLogin(String path){
		File file=new File(path);
		
		if (!file.exists())
		{
			
			createLoginInfoPreFile();
			System.out.println("***********LoginTimes.xml "+file.exists()+"************");
			Toast.makeText(MainActivity.this, welcomeSignString, 500).show();
		}
	}
	
	private void createSharedPreFile(){
		calendar=Calendar.getInstance();
		day=calendar.get(Calendar.DAY_OF_YEAR);
		SharedPreferences share=super.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=share.edit();
		editor.putString("language", "en");
		editor.putInt("LoginTimes", 0);
		editor.putInt("day",day);
		editor.commit();
	}
	private void createLoginInfoPreFile(){
		SharedPreferences share=super.getSharedPreferences(LOGINTIME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=share.edit();
		editor.putInt("LoginTimes", 1);
		editor.putString("language", "en");
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override //這�?必須�?要不然的�?案件無法�?��
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if ((System.currentTimeMillis() - exitTime) > 2000)
			{
				if (getLocaleLanguage() == "zh_CN")
				{
					Toast.makeText(getApplicationContext(), "再按�?���?��程序", Toast.LENGTH_SHORT).show();

				} else if (getLocaleLanguage() == "fr" || getLocaleLanguage() == "fr_CA")
				{
					Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
				}
				exitTime = System.currentTimeMillis();
			} else
			{
				MainActivity.this.finish();
				System.exit(0);
			}
			return true;

		} 
	return super.onKeyDown(keyCode, event); 
	} 
	
	public String getLocaleLanguage() {  
	    Locale l = Locale.getDefault();  
	    return String.format("%s-%s", l.getLanguage(), l.getCountry());  
	}  

}

package com.compassionapp.notification;

import java.util.Calendar;

import com.compassion.maininterface.R;
import com.compassionapp.maininterface.MainActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TimePicker.OnTimeChangedListener;
import autoRunService.ServiceTest;
import autoRunService.yourReceiver;

public class NotificationSetting extends Activity
{
	private AlarmManager alarmManager = null;
	private TimePicker timePicker = null;
	private Button setBtn = null;
	private Button delBtn = null;
	Calendar calendar = Calendar.getInstance();
	private TextView msg = null;
	public static int hourNow = 0;
	public static int minuteNow = 0;
	private static final String FILENAME="AppInfo";
	private SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_setting);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),"gothic.ttf");
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		setBtn = (Button) findViewById(R.id.button1);
		setBtn.setTypeface(tf);
		delBtn = (Button) findViewById(R.id.button2);
		delBtn.setTypeface(tf);
		// msg = (TextView) findViewById(R.id.textView1);
		this.setBtn.setOnClickListener(new setOnClickListenerImpl());
		this.delBtn.setOnClickListener(new delOnClickListenerImpl());
		// get the alarm service!
		this.alarmManager = (AlarmManager) super.getSystemService(Context.ALARM_SERVICE);
		this.timePicker.setOnTimeChangedListener(new OnTimeChangedListenerImpl());
		this.timePicker.setIs24HourView(true);
		sharedPreferences=super.getSharedPreferences(FILENAME,Activity.MODE_PRIVATE);
		editor=sharedPreferences.edit();
		int HourRegistered=sharedPreferences.getInt("Hour",Calendar.HOUR_OF_DAY);//Canlendar.Hour_Of_day 得到11？？
		int minRegistered=sharedPreferences.getInt("Minute",Calendar.MINUTE);//12？？�?
		System.out.println("hour"+HourRegistered+"min"+minRegistered);

		timePicker.setCurrentHour(HourRegistered);
		timePicker.setCurrentMinute(minRegistered);
		
		int times=sharedPreferences.getInt("LoginTimes", 0);
		if(times==1){
			new  AlertDialog.Builder(this)
			.setTitle("Notice" )
			.setMessage("It is your first time using this app, please set the time you prefer to receive the notification" )
			.setPositiveButton("OK" , null )
			.show();
			times++;
			editor.putInt("LoginTimes", times);
			editor.commit();
		};
		
	}

	private class OnTimeChangedListenerImpl implements OnTimeChangedListener
	{

		@Override
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
		{
			NotificationSetting.hourNow = hourOfDay; // save the hour
															// setting on the
															// TimePicker
			NotificationSetting.minuteNow = minute;// save the minute setting.	
		}

	}

	private class setOnClickListenerImpl implements OnClickListener
	{

		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			editor.putInt("Hour", NotificationSetting.hourNow);
			editor.putInt("Minute",NotificationSetting.minuteNow);
			editor.commit();
			Intent intent = new Intent(NotificationSetting.this, yourReceiver.class);
			intent.setAction("org.lxh.action.setalarm");
			PendingIntent sender = PendingIntent.getBroadcast(NotificationSetting.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			NotificationSetting.this.alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, NotificationSetting.this.calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender);
			//NotificationSetting.this.msg.setText("Time for Pushing Notification:" + NotificationSetting.hourNow + ":" + NotificationSetting.minuteNow);
			Toast.makeText(NotificationSetting.this, "Register Successfully", Toast.LENGTH_LONG).show();
			Intent intent1=new Intent(NotificationSetting.this,NotificationMain.class);
			NotificationSetting.this.startActivity(intent1);
			NotificationSetting.this.finish();

		}

	}

	private class delOnClickListenerImpl implements OnClickListener
	{

		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			NotificationSetting.this.stopService(new Intent(NotificationSetting.this, ServiceTest.class));
			Intent intent1=new Intent(NotificationSetting.this,NotificationMain.class);
			NotificationSetting.this.startActivity(intent1);
			NotificationSetting.this.finish();

		}

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
			Intent intent = new Intent();
			intent.setClass(NotificationSetting.this, NotificationMain.class);
			NotificationSetting.this.startActivity(intent);	
			NotificationSetting.this.finish();
		} 
	return super.onKeyDown(keyCode, event); 
	} 

}

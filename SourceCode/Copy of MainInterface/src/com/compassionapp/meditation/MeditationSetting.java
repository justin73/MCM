package com.compassionapp.meditation;

import com.compassion.maininterface.R;
import com.compassionapp.maininterface.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MeditationSetting extends Activity
{
	Spinner minSpinner = null;
	Spinner secSpinner = null;
	TextView titleTextView = null;
	TextView minTextView = null;
	TextView secTextView = null;
	Button confirmBtn = null;
	Button cancelBtn = null;
	int minValue;
	int secValue;
	SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.meditation_setting);
		Typeface tf = Typeface.createFromAsset(getAssets(),"gothic.ttf");
		minSpinner = (Spinner) findViewById(R.id.spinner1);
		secSpinner = (Spinner) findViewById(R.id.spinner2);
		titleTextView = (TextView) findViewById(R.id.titleTextview);
		titleTextView.setTypeface(tf);
		minTextView = (TextView) findViewById(R.id.minTextview);
		minTextView.setTypeface(tf);
		secTextView = (TextView) findViewById(R.id.secTextview);
		secTextView.setTypeface(tf);
		confirmBtn = (Button) findViewById(R.id.button1);
		confirmBtn.setTypeface(tf);
		cancelBtn = (Button) findViewById(R.id.button2);
		cancelBtn.setTypeface(tf);
		confirmBtn.setOnClickListener(new ConfirmListener());
		minSpinner.setOnItemSelectedListener(new OnMinSelectedListenerImpl());
		secSpinner.setOnItemSelectedListener(new OnSecSelectedListenerImpl());
	}

	private class OnMinSelectedListenerImpl implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View arg1, int position, long arg3)
		{
			// TODO Auto-generated method stub
			String minString = parent.getItemAtPosition(position).toString();
			minValue=Integer.parseInt(minString);
	        
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{
			// TODO Auto-generated method stub
			minValue=0;
		}

	}
	private class OnSecSelectedListenerImpl implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View arg1, int position, long arg3)
		{
			// TODO Auto-generated method stub
			String secString = parent.getItemAtPosition(position).toString();
			secValue=Integer.parseInt(secString);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{
			// TODO Auto-generated method stub
			secValue=0;
		}

	}
	private class ConfirmListener implements OnClickListener{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			sharedPreferences=MeditationSetting.super.getSharedPreferences("AppInfo",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor=sharedPreferences.edit();
			editor.putInt("MeditationMin", minValue);
			editor.putInt("MeditationSec", secValue);
			editor.commit();
			Intent intent=new Intent(MeditationSetting.this, countdownPage.class);
			MeditationSetting.this.startActivity(intent);
			MeditationSetting.this.finish();
			
			
		}
		
	}
	@Override //這�?必須�?要不然的�?案件無法�?��
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			Intent intent = new Intent();
			intent.setClass(MeditationSetting.this, MainActivity.class);
			MeditationSetting.this.startActivity(intent);	
			MeditationSetting.this.finish();
		} 
	return super.onKeyDown(keyCode, event); 
	} 
}

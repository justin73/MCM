package com.compassionapp.meditation;

import java.util.HashMap;
import java.util.Map;

import com.compassion.maininterface.R;
import com.compassionapp.maininterface.MainActivity;
import com.compassionapp.notification.NotificationMain;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter.LengthFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class countdownPage extends Activity
{
	private MyCount mc;
	private TextView tv;
	private ProgressBar hBar;
	private ProgressBar cBar;

	private ImageButton startBtn;
	SharedPreferences sharedPreferences;
	private int length;
	int minValue;
	int secValue;
	long leftTime;
	private boolean flag = false;
	private SoundPool soundPool;
	private Map<Integer, Integer> soundMap;
	private long exitTime = 0; 

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 防止屏幕上鎖
		setContentView(R.layout.countdown_timer);
		sharedPreferences = super.getSharedPreferences("AppInfo", Activity.MODE_PRIVATE);
		minValue = sharedPreferences.getInt("MeditationMin", 0);
		secValue = sharedPreferences.getInt("MeditationSec", 0);
		length = minValue * 1000 * 60 + secValue * 1000;
		tv = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),"gothic.ttf");
		tv.setTypeface(tf);
		if (secValue <= 9)
		{
			tv.setText(minValue + ":0" + secValue);
		} else
		{
			tv.setText(minValue + ":" + secValue);
		}

		tv.setClickable(true);
		tv.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (flag)
				{
					tv.setTextColor(Color.argb(255, 0, 0, 0));
					//設置字體顏色以及透明�?         透明度，R，G，B 此處為黑�?
					flag = false;
				} else
				{
					tv.setTextColor(Color.argb(50, 200, 200, 200));//透明度，值越大越不�?�?
					flag = true;
				}

			}
		});
		mc = new MyCount(length, 1000);

		startBtn = (ImageButton) findViewById(R.id.startBtn);
		// 建立�?��的媒体文�?
		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		soundMap = new HashMap<Integer, Integer>();
		soundMap.put(1, soundPool.load(countdownPage.this, R.raw.gong, 1));//mp3可以 WAV不识�?

		startBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// play ringtone
				/*int i = 0;
				while (i <= 1)
				{*/
					soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);//第四个参�?0次不循环 1次循环一�? 
				/*	i++;
				}*/

				/*
				 * Thread splashThread=new Thread(){ public void run(){ try {
				 * sleep(1000);
				 */
				mc.start();
				/*
				 * } catch (Exception e) { // TODO: handle exception } } };
				 * splashThread.start();
				 */
				startBtn.setVisibility(Button.INVISIBLE);
			}
		});

	}// end func

	/* å®šä¹‰ä¸€ä¸ªå€�?è®¡æ—¶çš�?å†�?éƒ¨ç±» */
	class MyCount extends CountDownTimer
	{
		public MyCount(long millisInFuture, long countDownInterval)
		{
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish()
		{
			soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);
			tv.setText("Done");
			startBtn.setVisibility(Button.VISIBLE);
		}

		@Override
		public void onTick(long millisUntilFinished)
		{
			/*
			 * while(millisUntilFinished>=1){ if }
			 */
			leftTime = millisUntilFinished;
			if ((millisUntilFinished - (millisUntilFinished / 60000) * 1000 * 60) / 1000 <= 9)
			{
				tv.setText(millisUntilFinished / 60000 + ":0" + (millisUntilFinished - (millisUntilFinished / 60000) * 1000 * 60) / 1000);
			} else
			{
				tv.setText(millisUntilFinished / 60000 + ":" + (millisUntilFinished - (millisUntilFinished / 60000) * 1000 * 60) / 1000);
			}
			// Toast.makeText(countdownPage.this, millisUntilFinished / 1000 +
			// "", Toast.LENGTH_LONG).show();// toastæœ‰æ˜¾ç¤ºæ�?¶é—´å»¶è¿�?
		}
	}
	@Override //這�?必須�?要不然的�?案件無法�?��
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{/*
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if (startBtn.getVisibility()==Button.INVISIBLE)
			{
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Quit?")
				.setMessage("Do you want to quit the meditation?")
				.setNegativeButton("No", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				}).setPositiveButton("Yes", new DialogInterface.OnClickListener()

				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						countdownPage.this.mc.cancel();
						Intent intent = new Intent();
						intent.setClass(countdownPage.this, MeditationSetting.class);
						countdownPage.this.startActivity(intent);	
						countdownPage.this.finish();
					}
				}).show();

				return true;
			}
			else{
				Intent intent = new Intent();
				intent.setClass(countdownPage.this, MeditationSetting.class);
				countdownPage.this.startActivity(intent);	
				countdownPage.this.finish();
				return super.onKeyDown(keyCode, event);
			}
		} else
		{
			Intent intent = new Intent();
			intent.setClass(countdownPage.this, MeditationSetting.class);
			countdownPage.this.startActivity(intent);	
			countdownPage.this.finish();
			return super.onKeyDown(keyCode, event);
		}
	*/
		if ((keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)||(keyCode==KeyEvent.KEYCODE_MENU&&event.getAction() == KeyEvent.ACTION_DOWN)
				||(keyCode==KeyEvent.KEYCODE_SEARCH&&event.getAction() == KeyEvent.ACTION_DOWN))
		{
			if (startBtn.getVisibility()==Button.INVISIBLE)
			{
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Quit?")
				.setMessage("Do you want to quit the meditation?")
				.setNegativeButton("No", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				}).setPositiveButton("Yes", new DialogInterface.OnClickListener()

				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						countdownPage.this.mc.cancel();
						Intent intent = new Intent();
						intent.setClass(countdownPage.this, MeditationSetting.class);
						countdownPage.this.startActivity(intent);	
						countdownPage.this.finish();
					}
				}).show();

				return true;
			}
			else{
				Intent intent = new Intent();
				intent.setClass(countdownPage.this, MeditationSetting.class);
				countdownPage.this.startActivity(intent);	
				countdownPage.this.finish();
				return super.onKeyDown(keyCode, event);
			}
		} else
		{
			Intent intent = new Intent();
			intent.setClass(countdownPage.this, MeditationSetting.class);
			countdownPage.this.startActivity(intent);	
			countdownPage.this.finish();
			return super.onKeyDown(keyCode, event);
		}
	
		}
}
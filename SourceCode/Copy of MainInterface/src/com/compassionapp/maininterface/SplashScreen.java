package com.compassionapp.maininterface;

import com.compassion.maininterface.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewFlipper;


public class SplashScreen extends Activity
{
	private boolean active=true;
	private int splashTime=2000;//duration of the splash screen
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.splashed_screen);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Thread splashThread=new Thread(){
			public void run(){
				try
				{
					int waited=0;
					while(active&&(waited<splashTime)){
						sleep(100);
						if(active){
							waited+=100;
						}
					}
				} catch (Exception e)
				{
					// TODO: handle exception
				}finally{
					finish();
					Intent intent=new Intent(SplashScreen.this, MainActivity.class);
					SplashScreen.this.startActivity(intent);
				}
			}
		};
		splashThread.start();
		
	}
	
}

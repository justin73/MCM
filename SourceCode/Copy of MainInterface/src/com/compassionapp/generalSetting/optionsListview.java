package com.compassionapp.generalSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.compassion.maininterface.R;
import com.compassionapp.maininterface.MainActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class optionsListview extends Activity
{
	private String dataEnglish[][] = new String[][]
	{
	{ "01", "Who is the MCM?" },
	{ "02", "FAQ" },
	{ "03", "Language" } };// define the listview data
	private String dataChinese[][] = new String[][]
	{
	{ "01", "MCM网站" },
	{ "02", "常见问题" },
	{ "03", "语言设置" } };// define the listview data
	/*private String dataFrench[][] = new String[][]
	{
	{ "01", "MCM website" },
	{ "02", "FAQ" },
	{ "03", "Font Setting" } };*/// define the listview data
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	// save the data into a list
	private ListView datalist;
	private SimpleAdapter simpleAdapter = null;
	private static final String FILENAME = "AppInfo";
	private static final String LOGINTIME="LoginTime";
	SharedPreferences sharedPreference;

	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.general_setting);
		this.datalist = (ListView) findViewById(R.id.datalist);
		sharedPreference = super.getSharedPreferences(LOGINTIME, Activity.MODE_PRIVATE);
		if ("zh".equalsIgnoreCase(sharedPreference.getString("language", null)))
		{
			for (int i = 0; i < this.dataChinese.length; i++)
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("_id", dataChinese[i][0]);
				map.put("name", dataChinese[i][1]);
				this.list.add(map);
			}

		}
		else if ("en".equalsIgnoreCase(sharedPreference.getString("language", null)))
		{
			for (int i = 0; i < this.dataEnglish.length; i++)
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("_id", dataEnglish[i][0]);
				map.put("name", dataEnglish[i][1]);
				this.list.add(map);
			}
		}
		
		this.simpleAdapter = new SimpleAdapter(this, this.list, R.layout.setting_datalist, new String[]
		{ "_id", "name" }, new int[]
		{ R.id._id, R.id.name });
		this.datalist.setAdapter(this.simpleAdapter);
		
		datalist.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) optionsListview.this.simpleAdapter.getItem(arg2);
				if (map.get("name") == "Who is the MCM?"||map.get("name")=="MCM网站")
				{
					goToUrl("http://www.montrealcitymission.org/en/");
				} else if (map.get("name") == "FAQ"||map.get("name")=="常见问题")
				{

					Intent intent = new Intent(optionsListview.this, FAQList.class);
					startActivity(intent);
					optionsListview.this.finish();

				} else
				{

					new AlertDialog.Builder(optionsListview.this)
					// 定义窗口名称
							.setTitle("Select Language")
							// 定义窗口列表
							.setItems(R.array.language_option, new DialogInterface.OnClickListener()
							{
								@SuppressLint("ShowToast")
								public void onClick(DialogInterface dialog, int which)
								{
									Toast.makeText(optionsListview.this, "Under development...", Toast.LENGTH_SHORT).show();
									/*
									String[] aryShop = getResources().getStringArray(R.array.language_option);
									sharedPreference = optionsListview.this.getSharedPreferences(LOGINTIME, Activity.MODE_PRIVATE);
									Toast.makeText(optionsListview.this, aryShop[which], 1000).show();
									if (aryShop[which].toString().equals("�?��中文"))
									{

										Resources resource = getResources();
										Configuration config = resource.getConfiguration();
										config.locale = Locale.SIMPLIFIED_CHINESE;
										getBaseContext().getResources().updateConfiguration(config, null);
										sharedPreference.edit().putString("language", "zh").commit();
										// 每次更改完语�?��，系统会强制重启应用 用来解决layout
										// label不能刷新的问�?
										Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
										i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(i);
										optionsListview.this.finish();
										
										 * Intent intent = new Intent();
										 * intent.setClass(optionsListview.this,
										 * MainActivity.class);
										 * intent.setFlags(Intent
										 * .FLAG_ACTIVITY_CLEAR_TOP);
										 * optionsListview
										 * .this.startActivity(intent);
										 * optionsListview.this.finish();
										 * Toast.makeText(optionsListview.this,
										 * "当前系统语言为中�?, 1000).show();
										 
									} else if (aryShop[which].toString().equals("English"))
									{
										Resources resource = getResources();
										Configuration config = resource.getConfiguration();
										config.locale = Locale.ENGLISH;
										getBaseContext().getResources().updateConfiguration(config, null);
										sharedPreference.edit().putString("language", "en").commit();
										Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
										i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(i);
										optionsListview.this.finish();// 每次跳转后都要将当前Activity终结
																		// 要不然的话会重现activity重复
										
										 * Intent intent = new Intent();
										 * intent.setClass(optionsListview.this,
										 * MainActivity.class);
										 * intent.setFlags(Intent
										 * .FLAG_ACTIVITY_CLEAR_TOP);
										 * optionsListview
										 * .this.startActivity(intent);
										 * optionsListview.this.finish();
										 
									} else
									{
										Toast.makeText(optionsListview.this, "failure", 1000).show();
									}

									
									 * Resources resource = getResources();
									 * Configuration config =
									 * resource.getConfiguration(); if
									 * (aryShop[which
									 * ].toString().equals("English")){
									 * 
									 * sharedPreference.edit().putString("language"
									 * , "en").commit(); config.locale =
									 * Locale.ENGLISH; } else if
									 * (aryShop[which].
									 * toString().equals("�?��中文")) {
									 * sharedPreference
									 * .edit().putString("language",
									 * "zh").commit(); config.locale =
									 * Locale.SIMPLIFIED_CHINESE; } else {
									 * sharedPreference
									 * .edit().putString("language",
									 * "fr").commit(); config.locale =
									 * Locale.getDefault(); }
									 * 
									 * getBaseContext().getResources().
									 * updateConfiguration(config, null);
									 * 
									 * Intent intent = new Intent();
									 * intent.setClass(optionsListview.this,
									 * MainActivity.class);
									 * intent.setFlags(Intent
									 * .FLAG_ACTIVITY_CLEAR_TOP);
									 * optionsListview
									 * .this.startActivity(intent);
									 
								*/}
							}).show();

				}
			}

		});

	}

	private void goToUrl(String url)
	{
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	@Override
	// 這�?必須�?要不然的�?案件無法�?��
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			optionsListview.this.finish();
			return true;
		} else
		{
			return super.onKeyDown(keyCode, event);
		}
	}
	public String getLocaleLanguage() {  
	    Locale l = Locale.getDefault();  
	    return String.format("%s-%s", l.getLanguage(), l.getCountry());  
	}  
}

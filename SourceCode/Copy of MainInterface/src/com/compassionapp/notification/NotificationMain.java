package com.compassionapp.notification;
import java.util.Calendar;
import org.xmlpull.v1.XmlPullParser;

import com.compassion.maininterface.R;
import com.compassionapp.maininterface.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class NotificationMain extends Activity
{
	private TextView quoteTitleTextView = null;
	public TextView quote = null;
	private TextView author = null;
	public String index = null;
	public static int quoteNo;
	//private int quoteIndex;
	private Calendar calendar;
	private int dayNow;
	private int dayInition;
	public String textforWidget=null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_layout);
		Typeface tf = Typeface.createFromAsset(getAssets(),"gothic.ttf");
		quoteTitleTextView = (TextView) findViewById(R.id.textView1);
		quoteTitleTextView.setTextSize(40);
		quoteTitleTextView.setTypeface(tf);
		quote = (TextView) findViewById(R.id.textView2);
		quote.setTypeface(tf);
		quote.setTextSize(28);
		author = (TextView) findViewById(R.id.textView4);
		author.setTypeface(tf);
		author.setTextSize(22);
		Resources resource = getResources();
		XmlResourceParser xrp = resource.getXml(R.xml.quote);
		SharedPreferences sharedPreferences = super.getSharedPreferences("AppInfo", Activity.MODE_PRIVATE);
		quoteNo = sharedPreferences.getInt("QuoteIndex", 0);
		calendar = Calendar.getInstance();
		dayNow = calendar.get(Calendar.DAY_OF_YEAR);
		dayInition = sharedPreferences.getInt("day", 0);
/*		System.out.println("今天�? + dayNow);
		System.out.println(sharedPreferences.getInt("day", 0) + "�?);
		System.out.println("两�?相差" + (dayNow - sharedPreferences.getInt("day", 0)) + "�?);*/
		quoteNo = dayNow - dayInition + 1;
/*		System.out.println("应该是第" + quoteNo + "条quote");*/
		try
		{
			// 当XML文件没有到达逻辑终点�?
			// 用getEventType获取当前事件

			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT)
			{
				if (xrp.getEventType() == XmlPullParser.START_TAG)
				{
					String name = xrp.getName();
					if (name.equals("quote"))
					{
						index = xrp.getAttributeValue(0);
						System.out.println(xrp.getAttributeValue(0));
						if (index.equals(Integer.toString(quoteNo)))
						{

							author.setText(xrp.getAttributeValue(1).toString());
							System.out.println(xrp.getAttributeValue(1).toString());

						} else
						{
							xrp.next();
						}
					}
				} else if (xrp.getEventType() == XmlPullParser.END_TAG)
				{
					// 控制台输出xml节点结束
					// System.out.println("****quote元素的属性遍历结束！");
				} // 当读取到xml节点是文本时
				else if (xrp.getEventType() == XmlPullParser.TEXT)
				{
					// 输出文本

					if (index.equals(Integer.toString(quoteNo)))
					{
						textforWidget=xrp.getText().toString();
						quote.setText(xrp.getText().toString());
						System.out.println(xrp.getText().toString());
					} else
					{
						xrp.next();
					}

				}
				xrp.next();

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub
		menu.add(Menu.NONE, Menu.FIRST + 1, 1, "Time Setting");
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub
		switch (item.getItemId())
		{
		case Menu.FIRST + 1:
			Intent intent = new Intent();
			intent.setClass(NotificationMain.this, NotificationSetting.class);
			NotificationMain.this.startActivity(intent);
			NotificationMain.this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override //這�?必須�?要不然的�?案件無法�?��
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			Intent intent = new Intent();
			intent.setClass(NotificationMain.this, MainActivity.class);
			NotificationMain.this.startActivity(intent);	
			NotificationMain.this.finish();
		} 
	return super.onKeyDown(keyCode, event); 
	} 
	

}

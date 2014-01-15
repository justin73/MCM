package autoRunService;

import java.util.Calendar;

import com.compassionapp.notification.NotificationMain;
import com.compassion.maininterface.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class ServiceTest extends Service
{
	Handler hd1 = new Handler();
	// ** 启动activity的开关 *//*
	boolean b;
	// 启动一次activity之后的一分钟内将不再重新启动
	int time;
	public static final Intent ACTION_START = null;
	private static final String TAG = "TestService";
	private SharedPreferences sharedPreferences;
	private static final String FILENAME = "AppInfo";
	private int hourSetting;
	private int minuteSetting;


	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public boolean onUnbind(Intent i)
	{
		Log.e(TAG, "============> TestService.onUnbind");
		return false;
	}

	@Override
	public void onRebind(Intent i)
	{
		Log.e(TAG, "============> TestService.onRebind");
	}

	@Override
	public void onCreate()
	{
		hd1.postDelayed(mTasks, delay);
		sharedPreferences = super.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
		hourSetting = sharedPreferences.getInt("Hour", 0);
		minuteSetting = sharedPreferences.getInt("Minute", 0);

	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		Log.e(TAG, "============> TestService.onStart");

	}

	@Override
	public void onDestroy()
	{
		Log.e(TAG, "============> TestService.onDestroy");

	}

	// ** 速度控制参数(单位豪秒)
	private int delay = 16000;//延迟30秒执行下一次检查 这样的话 就对应了下面的30秒重启的检查 所以这时就只响一声了
	// ** 控制速度

	private Runnable mTasks = new Runnable()
	{
		public void run()
		{
			log();
			hd1.postDelayed(mTasks, delay);
		}
	};

	@SuppressWarnings("deprecation")
	public void log()
	{
		Calendar c = Calendar.getInstance();
		int h = c.getTime().getHours();
		int m = c.getTime().getMinutes();
		Log.i("hour", "" + h);
		Log.i("minute", "" + m);
		// ** 这里的16和10可以自己定义一下 主要是提醒的时间设置，我不想做的太繁琐，所有没有些闹钟，只是用这个测试一下:) *//*
		if ((h == hourSetting && m == minuteSetting)/*
													 * ||(h>hourofBootTime)||(h==
													 * hourofBootTime
													 * &&m>=minofBootTime)
													 */)
		{
			// ** 为防止持续调用，所以用boolean 变量b做了一个小开关 *//*
			if (!b)
			{
				NotificationManager notificationManager = (NotificationManager) this.getSystemService(Activity.NOTIFICATION_SERVICE);
				Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gong);
				Notification notification = new Notification(R.drawable.action_icon, "A Message from My Compassion App", System.currentTimeMillis());
				notification.sound = sound;
				notification.defaults |= Notification.DEFAULT_VIBRATE;
				long[] vibrate =
				{0};
				notification.vibrate = vibrate;
				Intent intent = new Intent(this, NotificationMain.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				notification.setLatestEventInfo(this, "COMPASSION", "My Compassion Today", contentIntent);
				notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击后notification提示自动消失

				notificationManager.notify("MCM", R.drawable.action_bar, notification);
				b = true;
				stopSelf();
				//以下是SDK大于11后 有Notification.Builder可以用的方法
				/*
				 * Uri sound = Uri.parse("android.resource://" +
				 * getPackageName() + "/" + R.raw.bell); Notification.Builder
				 * notificationBuilder = new
				 * Notification.Builder(ServiceTest.this);
				 * notificationBuilder.setWhen(System.currentTimeMillis())
				 * .setSmallIcon(R.drawable.ic_launcher)
				 * .setContentTitle("MCM Message") .setSound(sound)
				 * .setContentText("What have you done today?"); long[] vibrate
				 * = { 0, 100, 200, 300 };
				 * notificationBuilder.setVibrate(vibrate); Intent intent = new
				 * Intent(this, NotificationMain.class);
				 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 * PendingIntent contentIntent = PendingIntent.getActivity(this,
				 * 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				 * notificationBuilder.setContentIntent(contentIntent); //
				 * setLatestEventInfo(this, "COMPASSION", //
				 * "What have you done today?", contentIntent); Notification
				 * notification = notificationBuilder.build();
				 * notification.flags |= Notification.FLAG_AUTO_CANCEL;//
				 * 点击后notification提示自动消失 notificationManager.notify("MCM",
				 * R.drawable.ic_launcher, notification); b = true;
				 * //stopSelf(); ServiceTest.this.stopSelf();
				 */
			}

		}
		// ** 开关开启后计时30秒，在这30秒之内就不再重新启动activity了，而60秒一过，上面的h和m条件肯定就不成立了 *//*
		if (b)
		{
			time += 1;
			if (time == 60)
			{
				time = 0;
				b = false;
			}
		}
	}

}

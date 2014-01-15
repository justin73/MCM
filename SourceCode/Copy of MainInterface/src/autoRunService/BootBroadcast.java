package autoRunService;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;

public class BootBroadcast extends BroadcastReceiver
{
	private SharedPreferences sharedPreferences;
	private Editor editor;

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context arg0, Intent arg1)
	{
		if (Intent.ACTION_BOOT_COMPLETED.equals(arg1.getAction())) {  
            // 启动完成  
			sharedPreferences =arg0.getSharedPreferences("AppInfo",   
                    Context.MODE_PRIVATE);   
            editor = sharedPreferences.edit();
			editor.putInt("HoursOfBootTime", new Date().getHours()); 
			editor.putInt("MinutesOfBootTime", new Date().getMinutes()); 
			editor.commit();
            Intent intent = new Intent(arg0, yourReceiver.class);  
            intent.setAction("org.lxh.action.setalarm");  
            PendingIntent sender = PendingIntent.getBroadcast(arg0, 0,  
                    intent, 0);  
            long firstime = SystemClock.elapsedRealtime();  
            AlarmManager am = (AlarmManager) arg0  
                    .getSystemService(Context.ALARM_SERVICE);  
  
            // 10秒一个周期，不停的发送广播  
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime,  
                    10 * 1000, sender);  
        }  
 
	} 

}

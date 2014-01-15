package autoRunService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class yourReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Intent i = new Intent(context, ServiceTest.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(i);
	}

}

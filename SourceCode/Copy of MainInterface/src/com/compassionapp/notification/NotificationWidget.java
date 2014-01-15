package com.compassionapp.notification;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.TextView;

public class NotificationWidget extends AppWidgetProvider {

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		for (int x = 0; x < appWidgetIds.length; x++) {
			Intent intent = new Intent(context,NotificationMain.class) ;
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			RemoteViews remote = new RemoteViews(context.getPackageName(),
					com.compassion.maininterface.R.layout.notification_widget);
			remote.setOnClickPendingIntent(com.compassion.maininterface.R.id.textView1, pendingIntent) ;
			appWidgetManager.updateAppWidget(appWidgetIds[x], remote) ;
		}
	}

}

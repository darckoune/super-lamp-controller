package com.example.darckoune.futuregadgetlamp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
    private static final String MyOnClick = "myOnClickTag";
    private static final String MyOnClick2 = "myOnClickTag2";
    private static final String MyOnClick3 = "myOnClickTag3";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds){

            Log.i("ONUPDATE", "FOR");

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);

            RemoteViews button = new RemoteViews(context.getPackageName(), R.layout.zone);
            button.setOnClickPendingIntent(R.id.onButton,
                    getPendingSelfIntent(context, MyOnClick2));
            views.addView(R.id.container, button);

            RemoteViews button2 = new RemoteViews(context.getPackageName(), R.layout.zone);
            button2.setOnClickPendingIntent(R.id.onButton,
                    getPendingSelfIntent(context, MyOnClick3));
            views.addView(R.id.container, button2);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (MyOnClick.equals(intent.getAction())){
            Log.i("WOW", "ON");
            new HttpTask().execute("http://home.darckoune.moe:8084/api/myGateway/Salon%20x3/ON");
        }

        if (MyOnClick2.equals(intent.getAction())){
            Log.i("WOW", "2");
        }

        if (MyOnClick3.equals(intent.getAction())){
            Log.i("WOW", "3");
        }
    }
}

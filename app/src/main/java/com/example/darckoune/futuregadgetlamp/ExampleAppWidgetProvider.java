package com.example.darckoune.futuregadgetlamp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
    private static final String MyOnClick = "myOnClickTag";
    private static final String MyOnClick2 = "myOnClickTag2";
    private static final String MyOnClick3 = "myOnClickTag3";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds){

            ZonesSingleton zonesSingleton = ZonesSingleton.getInstance();
            zonesSingleton.setGatewayName("myGateway");
            zonesSingleton.clearZones();
            zonesSingleton.addZone(new Zone(context, "Salon x3"));
            zonesSingleton.addZone(new Zone(context, "Salon x2"));
            zonesSingleton.addZone(new Zone(context, "Chambre"));

            Log.i("ONUPDATE", "FOR");

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
            views.removeAllViews(R.id.container);

            for (Zone zone : zonesSingleton.getZones()){
                RemoteViews zoneView = zone.getRemoteView();
                zoneView.setOnClickPendingIntent(R.id.onButton,
                        getPendingSelfIntent(context, zone.getOnAction()));
                zoneView.setOnClickPendingIntent(R.id.offButton,
                        getPendingSelfIntent(context, zone.getOffAction()));
                views.addView(R.id.container, zone.getRemoteView());
            }

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

        ZonesSingleton zonesSingleton = ZonesSingleton.getInstance();

        for (Zone zone : zonesSingleton.getZones()){
            Log.i("ACTION", intent.getAction());
            if (zone.getOnAction().equals(intent.getAction())){
                new HttpTask().execute(
                        "http://home.darckoune.moe:8084/api/" +
                                zonesSingleton.getGatewayName() +
                                "/" +
                                zone.getName() +
                                "/ON"
                );
            }
            if (zone.getOffAction().equals(intent.getAction())){
                new HttpTask().execute(
                        "http://home.darckoune.moe:8084/api/" +
                                zonesSingleton.getGatewayName() +
                                "/" +
                                zone.getName() +
                                "/OFF"
                );
            }
        }
    }
}

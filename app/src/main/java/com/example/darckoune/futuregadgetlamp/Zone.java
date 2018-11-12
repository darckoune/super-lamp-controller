package com.example.darckoune.futuregadgetlamp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class Zone {
    RemoteViews remoteView;
    String onAction;
    String offAction;
    String name;

    public Zone(Context context, String name) {
        this.name = name;
        this.onAction = "[" + name + "] ON";
        this.offAction = "[" + name + "] OFF";

        this.remoteView = new RemoteViews(context.getPackageName(), R.layout.zone);
    }

    public RemoteViews getRemoteView() {
        return remoteView;
    }

    public String getOnAction() {
        return onAction;
    }

    public String getOffAction() {
        return offAction;
    }

    public String getName() {
        return name;
    }
}

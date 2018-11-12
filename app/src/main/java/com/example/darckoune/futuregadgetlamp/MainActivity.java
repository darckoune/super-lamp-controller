package com.example.darckoune.futuregadgetlamp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText apiUrlEditor = (EditText) findViewById(R.id.ApiUrl);
        apiUrlEditor.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("ApiUrl", null));
    }

    public void updateZones(View v){
        new DatabaseUpdate(this, this);
        EditText apiUrlEditor = (EditText) findViewById(R.id.ApiUrl);
        String url = apiUrlEditor.getText().toString();

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("ApiUrl", url);
        editor.commit();
    }

    public void updateWidget(){
        Intent intent = new Intent(this, ExampleAppWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), ExampleAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
        displayToastSuccess();
    }

    private void displayToastSuccess() {
        Toast toast = Toast.makeText(getApplicationContext(), "Widget updated !", Toast.LENGTH_SHORT);
        toast.show();
    }

}
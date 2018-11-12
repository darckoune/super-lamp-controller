package com.example.darckoune.futuregadgetlamp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class DatabaseUpdate {
    private Context context;
    public DatabaseUpdate(Context context) {
        this.context = context;
        // call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute("http://home.darckoune.moe:8084/api/gateways");
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return com.example.darckoune.futuregadgetlamp.HttpGet.GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                Log.i("DATABASE UPDATE", "Creating editor...");
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                Log.i("DATABASE UPDATE", "Editor created");
                JSONArray gateways = new JSONArray(result);

                JSONObject gateway = gateways.getJSONObject(0);
                editor.putString("Gateway", gateway.getString("customName"));
                JSONArray zones = gateway.getJSONArray("zones");
                String zonesList = "";
                for (int i = 0; i < zones.length(); i++) {
                    JSONObject c = zones.getJSONObject(i);
                    String name = c.getString("name");
                    // adding zones
                    Log.i("DATABASE NEW ZONE", name);
                    zonesList += name;
                    if (i != zones.length() -1){
                        zonesList += ",";
                    }
                }
                editor.putString("Zones", zonesList);
                editor.commit();
            }
            catch(Exception e){
                Log.e("JSON Error", e.toString());
            }

        }
    }
}

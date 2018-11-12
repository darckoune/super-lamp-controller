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

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

                JSONArray gateways = new JSONArray(result);

                JSONObject gateway = gateways.getJSONObject(0);
                editor.putString("Gateway", gateway.getString("customName"));
                JSONArray zones = gateway.getJSONArray("zones");
                ArrayList<String> zonesList = new ArrayList<String>();
                for (int i = 0; i < zones.length(); i++) {
                    JSONObject c = zones.getJSONObject(i);
                    String name = c.getString("name");
                    // adding zones
                    zonesList.add(name);
                }
                Set<String> set = new HashSet<String>(zonesList);
                editor.putStringSet("Zones", set);
                editor.commit();
            }
            catch(Exception e){
                Log.e("JSON Error", e.toString());
            }

        }
    }
}

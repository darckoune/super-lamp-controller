package com.example.darckoune.futuregadgetlamp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... url) {
        try {
            Log.e("URL", url[0]);
            HttpURLConnection httpsURLConnection = (HttpURLConnection) new URL(url[0]).openConnection();
            httpsURLConnection.setConnectTimeout(2000);
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();
            int mStatus = httpsURLConnection.getResponseCode();
            if (mStatus == 200 || mStatus == 201)
                return readResponse(httpsURLConnection.getInputStream()).toString();
        } catch (IOException E) {
            Log.e("NETWORK ERROR", E.toString());
        }
        return null;
    }
    private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) stringBuilder.append(line);
        return stringBuilder;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
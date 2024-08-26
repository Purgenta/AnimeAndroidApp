package com.example.ispitnizadatak_animeapi.requests;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class GetRequest {
    public static void getJSON(String url, final ReadJson readJson) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                StringBuilder response = new StringBuilder();
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String chunk;
                    while ((chunk = buffer.readLine()) != null) {
                        response.append(chunk);
                    }
                    buffer.close();
                    connection.disconnect();
                } catch (Exception e) {
                    Log.d("Error during fetch", Objects.requireNonNull(e.getMessage()));
                }
                return response.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                readJson.setJson(s);
                readJson.sendEmptyMessage(0);
            }
        };
        task.execute(url);
    }
}

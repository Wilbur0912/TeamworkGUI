package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class GetData extends AsyncTask<String, String, String> {
    HashMap<String, String> resultAndDate = new HashMap<>();
    String date,year,month,result;
    int day;
    public String dateString;
    private static String Spring_URL = "http://172.20.10.2:3000/";
    @Override
    protected String doInBackground(String... string){
        String current ="";
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(Spring_URL);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                int data = isr.read();
                while (data != -1) {
                    current += (char) data;
                    data = isr.read();

                }
                return current;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return current;
    }

}

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.myapplication.GetData;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class watchHistory extends AppCompatActivity {


    HashMap<String, String> resultAndDate = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.historyresults);

        getHistoryData getData = new getHistoryData();
        Intent myIntent = getIntent();
        getData.dateString = myIntent.getStringExtra("date");
        getData.execute();
    }
    public class getHistoryData extends GetData {
        @Override
        protected void onPostExecute(String s){
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("parkinson");
                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    date = jsonObject1.getString("date");
                    String[] parts = date.split("/");
                    year = parts[0];
                    month = parts[1];
                    day = Integer.parseInt(parts[2]);
                    result = jsonObject1.getString("result");
                    Log.d("r",result);
                    // Hashmap

                    String d = year+"/"+month+"/"+day;

                    if(d.equals(dateString)){
                        Log.d("rss",result);
                        resultAndDate.put("result",d + " "+ result);
                        Log.d("susc",resultAndDate.get("result").toString());
                    }
                }
                TextView result = findViewById(R.id.Result);
                TextView date = findViewById(R.id.Date);
                Log.d("abccd",resultAndDate.get("result"));
                String[] DateAndResult = resultAndDate.get("result").split(" ");
                date.setText(DateAndResult[0]);
                result.setText(DateAndResult[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
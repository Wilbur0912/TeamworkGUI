package com.example.myapplication.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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


    private ListView lv;

    String date,year,month,result;
    int day;
    String dateString;
    private static String JSON_URL = "http://172.20.10.2:3000/";
    ArrayList<HashMap<String,String>> resultList;
    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historyresults);

        Intent myIntent = getIntent();
        dateString = myIntent.getStringExtra("date");

        resultList = new ArrayList<>();
        lv = findViewById(R.id.Listview);
        GetData getData = new GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... string){
            String current ="";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
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
                    HashMap<String, String> resultAndDate = new HashMap<>();
                    String d = year+"/"+month+"/"+day;
                    Log.d("date",dateString);
                    Log.d("dated",d);
                    Log.d("dateString",dateString);
                    if(d.equals(dateString)){
                        Log.d("rss",result);
                        resultAndDate.put("result",d + " "+ result);
                        Log.d("susc","suscc");
                        resultList.add(resultAndDate);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Displaying the results
            ListAdapter adapter = new SimpleAdapter(
                    watchHistory.this,
                    resultList,
                    R.layout.historyresults,
                    new String[] {"result"},
                    new int[]{R.id.textView});
            lv.setAdapter(adapter);
            String str = resultList.toString();
            Log.d("sus",str);
        }
    }
}
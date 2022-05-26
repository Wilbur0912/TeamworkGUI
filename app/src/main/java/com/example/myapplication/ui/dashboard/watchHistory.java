package com.example.myapplication.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
    String namey, age;
    private static String JSON_URL = "http://172.20.10.2:3000/";
    ArrayList<HashMap<String,String>> friendsList;
    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        Intent myIntent = getIntent();
        //TextView date = findViewById(R.id.date);
        //date.setText(myIntent.getStringExtra("date"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historyresults);


        friendsList = new ArrayList<>();
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
                JSONArray jsonArray = jsonObject.getJSONArray("Friends");
                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    age = jsonObject1.getString("age");
                    namey = jsonObject1.getString("name");

                    // Hashmap
                    HashMap<String, String> friends = new HashMap<>();
                    friends.put("name",age+": "+ namey);
                    friends.put("age",age);
                    friendsList.add(friends);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Displaying the results
            ListAdapter adapter = new SimpleAdapter(
                    watchHistory.this,
                    friendsList,
                    R.layout.historyresults,
                    new String[] {"name","age"},
                    new int[]{R.id.textView});
            lv.setAdapter(adapter);
        }
    }
}
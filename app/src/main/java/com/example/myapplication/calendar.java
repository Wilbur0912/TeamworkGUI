package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class calendar extends AppCompatActivity {
    CalendarView calendarView;
    TextView myDate;
    Button confirm;
    String thisdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_dashboard);

        calendarView = (CalendarView) findViewById(R.id.calendarView);

        myDate = (TextView) findViewById(R.id.myDate);

        Calendar calendar = Calendar.getInstance();
        String thisMonth = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String thisYear = String.valueOf(calendar.get(Calendar.YEAR));
        String today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        thisdate = thisYear + "/" + thisMonth + "/" + today;
        getHistoryData getData = new getHistoryData();
        getData.execute();
        //myDate.setText(thisdate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                thisdate = year+"/"+(month+1)+"/"+dayOfMonth;
                //myDate.setText(thisdate);
                getHistoryData getData = new getHistoryData();
                getData.execute();
            }
        });
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

                    if(d.equals(thisdate)){
                        Log.d("rss",result);
                        resultAndDate.put("result",d + " "+ result);
                        Log.d("susc",resultAndDate.get("result").toString());
                    }
                }
                TextView result = findViewById(R.id.Result);
                TextView date = findViewById(R.id.myDate);
                Log.d("abccd",resultAndDate.get("result"));
                String[] DateAndResult = resultAndDate.get("result").split(" ");
                date.setText(DateAndResult[0]);
                if(DateAndResult[1].equals("無異狀")){
                    result.setTextColor(Color.parseColor("#36AE7C"));
                    result.setText(DateAndResult[1]);
                }
                else{
                    result.setTextColor(Color.parseColor("#EB5353"));
                    result.setText(DateAndResult[1]);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class calendar extends AppCompatActivity {
    CalendarView calendarView;
    TextView myDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_dashboard);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month+1)+"/"+dayOfMonth+"/"+year;
                myDate.setText(date);
            }
        });
    }




}
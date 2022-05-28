package com.example.myapplication.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.Calendar;

public class calendar extends AppCompatActivity {
    CalendarView calendarView;
    TextView myDate;
    Button confirm;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_dashboard);

        calendarView = (CalendarView) findViewById(R.id.calendarView);

        myDate = (TextView) findViewById(R.id.myDate);
        confirm = (Button) findViewById(R.id.button);
        Calendar calendar = Calendar.getInstance();
        String thisMonth = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String thisYear = String.valueOf(calendar.get(Calendar.YEAR));
        String today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        date = thisYear + "/" + thisMonth + "/" + today;
        myDate.setText(date);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(calendar.this, com.example.myapplication.ui.dashboard.watchHistory.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year+"/"+(month+1)+"/"+dayOfMonth;
                myDate.setText(date);
            }
        });
    }




}
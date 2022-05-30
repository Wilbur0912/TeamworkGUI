package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.firebase.messaging.FirebaseMessaging;

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
import java.util.Calendar;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;

    private static final String TAG = "MainActivity";
    private LineChart mChart;
    int week1 = 0;
    int week2 = 0;
    int week3 = 0;
    int week4 = 0;
    int week5 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseGetToken();
        navigationBar();
        showChart showchart = new showChart();
        showchart.execute();
    }

    class showChart extends GetData {
        JSONObject jsonObject;
        JSONArray jsonArray;

        @Override
        protected void onPostExecute(String s){
            try{
                Calendar calendar = Calendar.getInstance();
                String thisMonth = String.valueOf(calendar.get(Calendar.MONTH)+1);
                String thisYear = String.valueOf(calendar.get(Calendar.YEAR));
                Log.d("month",thisYear+"/"+thisMonth);

                jsonObject = new JSONObject(s);
                jsonArray = jsonObject.getJSONArray("parkinson");
                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    date = jsonObject1.getString("date");
                    String[] parts = date.split("/");
                    year = parts[0];
                    month = parts[1];
                    day = Integer.parseInt(parts[2]);
                    result = jsonObject1.getString("result");

                    // Hashmap
                    if(result.equals("有異狀")&&year.equals(thisYear)&&month.equals(thisMonth)){
                        if(1<=day && day<=7){
                            week1 = week1 + 1;
                        }
                        else if(8<=day && day<=14){
                            week2 = week2 + 1;
                        }
                        else if(15<=day && day<=21){
                            week3 = week3 + 1;
                        }
                        else if(22<=day && day<=28){
                            week4 = week4 + 1;
                        }
                        else if(29<=day){
                            week5 = week5 + 1;
                        }
                        makeChart();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        private void makeChart(){
            mChart = (LineChart) findViewById(R.id.linechart);
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(false);
            ArrayList<Entry> yValues = new ArrayList<Entry>();
            int a = 1;
            yValues.add(new Entry(0,week1));
            yValues.add(new Entry(1,week2));
            yValues.add(new Entry(2,week3));
            yValues.add(new Entry(3,week4));
            //yValues.add(new Entry(4,week5));
            LineDataSet set1 = new LineDataSet(yValues,"Data Set 1");
            set1.setFillAlpha(110);
            set1.setColor(Color.RED);
            set1.setDrawFilled(true);
            set1.setLineWidth(3f);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }
    }

    public void navigationBar(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId() == R.id.navigation_dashboard) {
                    Intent intent = new Intent(MainActivity.this, com.example.myapplication.ui.dashboard.calendar.class);
                    startActivity(intent);
                } else {

                }
            }

        });
    }
    public void firebaseGetToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        String token = task.getResult();

                        Log.d("tokendd", token);

                    }

                });
    }


}





package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.view.Window;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
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
                TextView dateTitle = findViewById(R.id.date);
                dateTitle.setText("June");
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
        private void makeChart() {
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
            LineDataSet set1 = new LineDataSet(yValues,"June Data");
            set1.setFillAlpha(110);
            set1.setColor(Color.RED);
            set1.setDrawFilled(true);
            set1.setLineWidth(3f);
            set1.setValueTextSize(25);//座標點數字大小
            set1.setValueFormatter(new DefaultValueFormatter(0));//座標點數字的小數位數1位

            XAxis xAxis = mChart.getXAxis();
            xAxis.setSpaceMin(0.1f);//折線起點距離左側Y軸距離
            xAxis.setSpaceMax(0.1f);//折線終點距離右側Y軸距離
            xAxis.setDrawGridLines(false);//不顯示每個座標點對應X軸的線 (預設顯示)
            xAxis.enableGridDashedLine(5f, 5f, 0f); //格線以虛線顯示，可設定虛線長度、間距等，如setDrawGridLines(false)則此設定無效
            xAxis.setGridLineWidth(2f);//格線寬度

            String[] xValue = new String[]{"Week1", "Week2", "Week3", "Week4"};
            List<String> xList = new ArrayList<>();
            for (int i = 0; i < xValue.length; i++) {
                xList.add(xValue[i]);
//            xList.add(String.valueOf(i +1).concat("月"));
            }
            /**
             * 格式化軸標籤二種方式：
             * 1、用圖表庫已寫好的類_如下X 軸使用
             * 2、自己實現接口_下一步驟中Y 軸使用
             * */
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
            YAxis rightAxis = mChart.getAxisRight();//獲取右側的軸線
            rightAxis.setEnabled(false);//不顯示右側Y軸
            YAxis leftAxis = mChart.getAxisLeft();//獲取左側的軸線
            //leftAxis.setValueFormatter(new MyYAxisValueFormatter());
            leftAxis.setGranularity(1f);//Y軸數值的間隔

            Description description = mChart.getDescription();
            description.setEnabled(false);//不顯示Description Label (預設顯示)
            description.setText("adsf");

            mChart.setPinchZoom(true); // true->X、Y軸同時按比例縮放、false:X、Y可單獨縮放

            mChart.setDrawBorders(true);//顯示邊框 (預設不顯示)
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
                    Intent intent = new Intent(MainActivity.this, calendar.class);
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





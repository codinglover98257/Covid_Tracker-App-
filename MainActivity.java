package com.javatpoint.covidtrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView total_confirm,total_tests,total_recovered,total_death,total_Active;
    TextView today_confirm,today_recovered,today_death,tv_date;
    PieChart mPieChart;
    String country = "India";
    TextView c_name;

    private List<CountryData> countryDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        countryDataList = new ArrayList<>();

        if(getIntent().getStringExtra("country") != null) {

            country = getIntent().getStringExtra("country");
        }

          Init();

          c_name = (TextView)findViewById(R.id.tv_2);
          c_name.setText(country);

          c_name.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                   startActivity(new Intent(MainActivity.this, CountryActivity.class));
              }
          });


        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {

                countryDataList.addAll(response.body());

                for(int i=0; i < countryDataList.size(); i++) {

                     if(countryDataList.get(i).getCountry().equals(country)) {

                         int confirm = Integer.parseInt(countryDataList.get(i).getCases());
                         int Active = Integer.parseInt(countryDataList.get(i).getCases());
                         int recovered = Integer.parseInt(countryDataList.get(i).getCases());
                         int death = Integer.parseInt(countryDataList.get(i).getCases());

                         total_confirm.setText(NumberFormat.getInstance().format(confirm));
                         total_Active.setText(NumberFormat.getInstance().format(Active));
                         total_recovered.setText(NumberFormat.getInstance().format(recovered));
                         total_death.setText(NumberFormat.getInstance().format(death));

                         today_confirm.setText(NumberFormat.getInstance().format(Integer.parseInt(countryDataList.get(i).getTodayCases())));
                         today_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(countryDataList.get(i).getTodayRecovered())));
                         today_death.setText(NumberFormat.getInstance().format(Integer.parseInt(countryDataList.get(i).getTodayDeaths())));
                         total_tests.setText(NumberFormat.getInstance().format(Integer.parseInt(countryDataList.get(i).getTests())));

                         setText(countryDataList.get(i).getUpdated());

                         mPieChart.addPieSlice(new PieModel("confirm",confirm, getResources().getColor(R.color.yellow)));
                         mPieChart.addPieSlice(new PieModel("Active", Active, getResources().getColor(R.color.blue_pie)));
                         mPieChart.addPieSlice(new PieModel("recovered", recovered, getResources().getColor(R.color.green_pie)));
                         mPieChart.addPieSlice(new PieModel("death", death, getResources().getColor(R.color.red_pie)));

                         mPieChart.startAnimation();
                     }

                }
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {

                Toast.makeText(MainActivity.this,"Error : "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setText(String updated) {

        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");

        long ms = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        tv_date.setText("Update at "+format.format(calendar.getTime()));
    }

    private void Init() {

        tv_date = (TextView)findViewById(R.id.tv_3);
        total_confirm = (TextView)findViewById(R.id.tv_9);
        total_Active = (TextView)findViewById(R.id.tv_11);
        total_recovered = (TextView)findViewById(R.id.tv_14);
        total_death = (TextView)findViewById(R.id.tv_17);
        total_tests = (TextView)findViewById(R.id.tv_20);
        today_confirm = (TextView)findViewById(R.id.tv_22);
        today_recovered = (TextView)findViewById(R.id.tv_15);
        today_death = (TextView)findViewById(R.id.tv_18);
        mPieChart = (PieChart) findViewById(R.id.piechart);

    }
}
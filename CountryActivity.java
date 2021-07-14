package com.javatpoint.covidtrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CountryData> countryDataList;
    ProgressDialog progressDialog;
    EditText et_search;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        et_search = (EditText)findViewById(R.id.et_search);
        countryDataList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new MyAdapter(this,countryDataList);
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {

                 countryDataList.addAll(response.body());

                 adapter.notifyDataSetChanged();

                 progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {

                progressDialog.dismiss();

                Toast.makeText(CountryActivity.this, ""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                 filter(s.toString());
            }
        });
    }

    private void filter(String text) {

        List<CountryData> filter_list = new ArrayList<>();

        for(CountryData list : countryDataList) {

            if(list.getCountry().toLowerCase().contains(text.toLowerCase())) {

                filter_list.add(list);
            }
        }

        adapter.filterList(filter_list);
    }
}
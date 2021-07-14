package com.javatpoint.covidtrackerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    List<CountryData> countryDataList;

    public MyAdapter(Context context, List<CountryData> countryDataList) {
        this.context = context;
        this.countryDataList = countryDataList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    public void filterList(List<CountryData> filterList) {

        countryDataList = filterList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MyAdapter.ViewHolder holder, int position) {

           CountryData data = countryDataList.get(position);

           holder.tv_index.setText(String.valueOf(position+1));
           holder.tv_name.setText(data.getCountry());
           holder.tv_case.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));

           Map<String, String> img = data.getCountryInfo();
           Glide.with(context).load(img.get("flag")).into(holder.imageView);


           holder.cardView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Intent intent = new Intent(context, MainActivity.class);
                   intent.putExtra("country",data.getCountry());
                   context.startActivity(intent);
               }
           });

    }

    @Override
    public int getItemCount() {
        return countryDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv_index,tv_name,tv_case;
        CardView cardView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_index = (TextView)itemView.findViewById(R.id.tv_index);
            tv_name = (TextView)itemView.findViewById(R.id.tv_name);
            tv_case = (TextView)itemView.findViewById(R.id.tv_case);
            imageView = (ImageView)itemView.findViewById(R.id.image_view);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}

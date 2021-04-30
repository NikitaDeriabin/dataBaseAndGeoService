package com.example.labmob2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final String AREA_UNIT = "<sup><small>2</sup></small>";
    private final int MILLION_LENGTH = 7;

    private Context context;
    Activity activity;
    private ArrayList region_id, region_title, region_center, region_population, region_area;

    CustomAdapter(Activity activity, Context context, ArrayList region_id, ArrayList region_title,
                  ArrayList region_center, ArrayList region_population, ArrayList region_area){

        this.activity = activity;
        this.context = context;
        this.region_id = region_id;
        this.region_title = region_title;
        this.region_center = region_center;
        this.region_population = region_population;
        this.region_area = region_area;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.region_id.setText(String.valueOf(region_id.get(position)));
        holder.region_title.setText(String.valueOf(region_title.get(position)));
        holder.region_center.setText(String.valueOf(region_center.get(position)));
        holder.region_population.setText(normalizePopulation(String.valueOf(region_population.get(position))) + "млн");
        holder.region_area.setText(Html.fromHtml(String.valueOf(region_area.get(position)) + "км" + AREA_UNIT));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(region_id.get(position)));
                intent.putExtra("title", String.valueOf(region_title.get(position)));
                intent.putExtra("center", String.valueOf(region_center.get(position)));
                intent.putExtra("population", String.valueOf(region_population.get(position)));
                intent.putExtra("area", String.valueOf(region_area.get(position)));

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return region_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView region_id, region_title, region_center, region_population, region_area;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            region_title = itemView.findViewById(R.id.region_title);
            region_center = itemView.findViewById(R.id.region_center);
            region_population = itemView.findViewById(R.id.population);
            region_area = itemView.findViewById(R.id.area);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }

    }

    private String normalizePopulation(String text){
        int size = text.length();
        StringBuffer temp = new StringBuffer(text);
        String result;
        boolean flag = false; // size >= MILLION_LENGTH
        int diff;
        diff = Math.abs(size - MILLION_LENGTH);
        flag = size >= MILLION_LENGTH;

        if(!flag){
            for(int i = 0; i < diff; i++){
                temp.insert(0, "0");
            }
            try{
               result = temp.substring(0, 1) + ',' + temp.substring(1, 1 + 3);
            }catch (Exception ex){
                result = text;
            }
        }
        else{
            result = text.substring(0, diff + 1) + ',' + text.substring(diff + 1, diff + 1 + 3);
        }

        return result;
    }
}

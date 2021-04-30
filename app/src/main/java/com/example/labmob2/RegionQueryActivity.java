package com.example.labmob2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RegionQueryActivity extends AppCompatActivity {

    private final int MILLION_LENGTH = 7;

    RecyclerView recyclerView;
    TextView query_text, query_text2, average_population;

    DbHelper db;
    ArrayList<String> region_id, region_title, region_center, region_population, region_area;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_query);

        recyclerView = findViewById(R.id.query_recycler);
        query_text = findViewById(R.id.query_text);
        query_text2 = findViewById(R.id.query_text2);
        average_population = findViewById(R.id.average_population);

        db = new DbHelper(RegionQueryActivity.this);
        region_id = new ArrayList<>();
        region_title = new ArrayList<>();
        region_center= new ArrayList<>();
        region_population = new ArrayList<>();
        region_area = new ArrayList<>();

        query_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(recyclerView.getVisibility() == View.GONE){
                    getQueryData();

                    customAdapter = new CustomAdapter(RegionQueryActivity.this, RegionQueryActivity.this, region_id,
                            region_title, region_center, region_population, region_area);
                    recyclerView.setAdapter(customAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RegionQueryActivity.this));

                    recyclerView.setVisibility(View.VISIBLE);
               }else {
                   recyclerView.setVisibility(View.GONE);
                   clearData();
               }
            }
        });

        query_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(average_population.getVisibility() == View.GONE){

                    setAvaragePopulation();
                    average_population.setVisibility(View.VISIBLE);
                }else{
                    average_population.setVisibility(View.GONE);
                }
            }
        });



    }

    void getQueryData(){
        Cursor cursor = db.readQueryData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data" , Toast.LENGTH_LONG).show();
        } else {
            while(cursor.moveToNext()){
                region_id.add(cursor.getString(0));
                region_title.add(cursor.getString(1));
                region_center.add(cursor.getString(2));
                region_population.add(cursor.getString(3));
                region_area.add(cursor.getString(4));
            }
        }
    }

    void clearData(){
        region_id.clear();
        region_title.clear();
        region_center.clear();
        region_population.clear();
        region_area.clear();
    }

    void setAvaragePopulation(){
        Cursor cursor = db.getAvaragePopulation();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data" , Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToNext();
            double res = cursor.getDouble(0);
            average_population.setText(normalizePopulation(String.valueOf((int)res)) + "млн");
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
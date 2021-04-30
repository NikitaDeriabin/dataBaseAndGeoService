package com.example.labmob2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Region;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;

    DbHelper db;
    ArrayList<String> region_id, region_title, region_center, region_population, region_area;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        db = new DbHelper(MainActivity.this);
        region_id = new ArrayList<>();
        region_title = new ArrayList<>();
        region_center= new ArrayList<>();
        region_population = new ArrayList<>();
        region_area = new ArrayList<>();

        getAllData();

        customAdapter = new CustomAdapter(MainActivity.this, this, region_id,
                region_title, region_center, region_population, region_area);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void getAllData(){
        Cursor cursor = db.readAllData();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.region_query:
                //Toast.makeText(this, "region_query", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, RegionQueryActivity.class);
                startActivity(intent);
                break;
            case R.id.contacts_query:
                //Toast.makeText(this, "contacts_query", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent2);
                break;
            case R.id.geoservice:
                //Toast.makeText(this, "geoservice", Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent3);
                break;
            case R.id.about:
                //Toast.makeText(this, "geoservice", Toast.LENGTH_LONG).show();
                Intent intent4 = new Intent(MainActivity.this, About.class);
                startActivity(intent4);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
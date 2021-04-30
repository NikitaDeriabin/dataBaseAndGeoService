package com.example.labmob2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText region_title, region_center, population, area;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        region_title = findViewById(R.id.region_title);
        region_center = findViewById(R.id.region_center_title);
        area = findViewById(R.id.area);
        population = findViewById(R.id.population);
        addButton = findViewById(R.id.add_button);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(AddActivity.this);
                db.addRegion(region_title.getText().toString().trim(),
                        region_center.getText().toString().trim(),
                        Integer.valueOf(population.getText().toString().trim()),
                        Float.valueOf(area.getText().toString().trim()));
            }
        });

    }
}
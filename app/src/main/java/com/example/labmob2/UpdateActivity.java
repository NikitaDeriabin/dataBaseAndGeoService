package com.example.labmob2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_upd, center_upd, population_upd, area_upd;
    Button update_button, delete_button;
    String id, title, center, population, area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_upd = findViewById(R.id.region_title_upd);
        center_upd = findViewById(R.id.region_center_title_upd);
        population_upd = findViewById(R.id.population_upd);
        area_upd = findViewById(R.id.area_upd);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        //set action bar title in update activity
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(UpdateActivity.this);

                title = title_upd.getText().toString().trim();
                center = center_upd.getText().toString().trim();
                population = population_upd.getText().toString().trim();
                area = area_upd.getText().toString().trim();

                db.updateDate(id, title, center, Integer.valueOf(population), Float.valueOf(area));

            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")
                && getIntent().hasExtra("center")
                && getIntent().hasExtra("title")
                && getIntent().hasExtra("population")
                && getIntent().hasExtra("area")){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            center = getIntent().getStringExtra("center");
            population = getIntent().getStringExtra("population");
            area = getIntent().getStringExtra("area");

            //Set data from intent
            title_upd.setText(title);
            center_upd.setText(center);
            population_upd.setText(population);
            area_upd.setText(area);

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning" );
        builder.setMessage("Delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbHelper db = new DbHelper(UpdateActivity.this);
                db.deleteData(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
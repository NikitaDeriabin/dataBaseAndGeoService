package com.example.labmob2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "UkraineRegions.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "ukraine_regions";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "region_title";
    private static final String COLUMN_POPULATION = "population";
    private static final String COLUMN_AREA = "area";
    public static final String COLUMN_REGIONAL_CENTER = "regional_center";

    private static final int MILLION = 1000000;

    DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_REGIONAL_CENTER + " TEXT," +
                        COLUMN_POPULATION + " INTEGER," +
                        COLUMN_AREA + " REAL);";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addRegion(String title, String regionCenter, int population, float area){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_REGIONAL_CENTER, regionCenter);
        cv.put(COLUMN_AREA, area);
        cv.put(COLUMN_POPULATION, population);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed with adding", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }

    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    void updateDate(String row_id, String title, String regionCenter, int population, float area){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_REGIONAL_CENTER, regionCenter);
        cv.put(COLUMN_AREA, area);
        cv.put(COLUMN_POPULATION, population);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed with updating", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }
    }

    void deleteData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed with deleting", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }

    }

    Cursor readQueryData(){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_POPULATION + " <= " + MILLION ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor getAvaragePopulation(){
        String query = "SELECT AVG(" + COLUMN_POPULATION + ") " + "FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor getAllRegionCenter(){
        String query = "SELECT " + COLUMN_REGIONAL_CENTER + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
}

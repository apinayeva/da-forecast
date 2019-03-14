package com.example.gannapinaieva.da_forecast;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // For DB
    private String tableName = "da_forecast";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB_v1", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table da_forecast ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "temperature text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteAllCities() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + tableName + ";";
        db.execSQL(query);
    }

    public void deleteCity(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + tableName + " WHERE name = '" + name + "';";
        db.execSQL(query);
    }

    public ArrayList<Record> getCityForecastList() throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT name, temperature FROM " + tableName;
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Record> cityList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Record city = new Record(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("temperature")));
            cityList.add(city);
        }
        cursor.close();
        return cityList;
    }
}
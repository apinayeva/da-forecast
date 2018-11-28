package com.example.gannapinaieva.da_forecast;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity { //implements OnClickListener {

    ListView cityList;
    TextView cityName, tvColor, tvSize;
    Button findCityButton, openNewWindowButton;

    ListAdapter adapter;

    final int MENU_COLOR_RED = 1;
    final int MENU_COLOR_GREEN = 2;
    final int MENU_COLOR_BLUE = 3;

    final int MENU_SIZE_22 = 4;
    final int MENU_SIZE_26 = 5;
    final int MENU_SIZE_30 = 6;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        // Get reference of widgets from XML layout
        cityList = findViewById(R.id.city_list);
        cityName = findViewById(R.id.find_city_field);
        findCityButton = findViewById(R.id.find_button);
        findCityButton.setBackgroundResource(R.color.llTopColor);
        findCityButton.setEnabled(true);

        openNewWindowButton = findViewById(R.id.moreDetails);

        tvColor = findViewById(R.id.tvColor);
        tvSize = findViewById(R.id.tvSize);

        // для tvColor и tvSize необходимо создавать контекстное меню
        registerForContextMenu(tvColor);
        registerForContextMenu(tvSize);

        ArrayList<HashMap<String, String>> userList = getCityForecastList();
        adapter = new SimpleAdapter(MainActivity.this, userList, R.layout.list_raw,new String[]{"name","temperature"}, new int[]{R.id.name, R.id.temp});
        cityList.setAdapter(adapter);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Test.class);
                intent.putExtra("city_name", parent.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });

        View.OnClickListener saveToDB = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create object for data
                ContentValues cv = new ContentValues();
                // Connect to DB
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                cv.put("name", cityName.getText().toString());
                cv.put("temperature", "-1");
                // вставляем запись и получаем ее ID
                long rowID = db.insert("da_forecast", null, cv);
                Toast.makeText(MainActivity.this, "row inserted, ID = " + rowID, Toast.LENGTH_LONG).show();
                cityName.setText("");

                // TODO: add auto update
//                ((SimpleAdapter) adapter).notifyDataSetChanged();
//                arrayAdapter.notifyDataSetChanged();
            }
        };
        findCityButton.setOnClickListener(saveToDB);

        View.OnClickListener moreDetailsAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add new logic
            }
        };
        openNewWindowButton.setOnClickListener(moreDetailsAction);
    }

    public ArrayList<HashMap<String, String>> getCityForecastList(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, temperature FROM da_forecast";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex("name")));
            user.put("temperature",cursor.getString(cursor.getColumnIndex("temperature")));
            userList.add(user);
        }
        return  userList;
    }

    // TODO: implement this way to process onClick
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.moreDetails:
//
//                break;
//
//        }
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tvColor:
                menu.add(0, MENU_COLOR_RED, 0, "Red");
                menu.add(0, MENU_COLOR_GREEN, 0, "Green");
                menu.add(0, MENU_COLOR_BLUE, 0, "Blue");
                break;
            case R.id.tvSize:
                menu.add(0, MENU_SIZE_22, 0, "22");
                menu.add(0, MENU_SIZE_26, 0, "26");
                menu.add(0, MENU_SIZE_30, 0, "30");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            // пункты меню для tvColor
            case MENU_COLOR_RED:
                tvColor.setTextColor(Color.RED);
                tvColor.setText("Text color = red");
                break;
            case MENU_COLOR_GREEN:
                tvColor.setTextColor(Color.GREEN);
                tvColor.setText("Text color = green");
                break;
            case MENU_COLOR_BLUE:
                tvColor.setTextColor(Color.BLUE);
                tvColor.setText("Text color = blue");
                break;
// пункты меню для tvSize
            case MENU_SIZE_22:
                tvSize.setTextSize(22);
                tvSize.setText("Text size = 22");
                break;
            case MENU_SIZE_26:
                tvSize.setTextSize(26);
                tvSize.setText("Text size = 26");
                break;
            case MENU_SIZE_30:
                tvSize.setTextSize(30);
                tvSize.setText("Text size = 30");
                break;
        }
        return super.onContextItemSelected(item);
    }
}

//Toast.makeText(this, "Нажата кнопка Cancel", Toast.LENGTH_LONG).show();

// How to set on button click
/*
        View.OnClickListener searchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                fruits_list.add(new Record(3, cityName.getText().toString(), "10"));
                arrayAdapter.notifyDataSetChanged();

            }
        };

        findCityButton.setOnClickListener(searchBtn);
*/


//        ArrayList<HashMap<String, String>> userList = null;//= getCityForecastList();
//        int[] ids = { R.id.name, R.id.temp };
//        HashMap<String, String> hash1 = new HashMap<String, String>();
//        hash1.put("name","Kiev");
//        hash1.put("temperature", "0");
//
//        HashMap<String, String> hash2 = new HashMap<String, String>();
//        hash2.put("name","Kharkov");
//        hash2.put("temperature", "-1");
//
//        ArrayList<HashMap<String, String>> websites = new ArrayList<HashMap<String, String>> ();
//        String[] keys = { "name","temperature" };
//        SimpleAdapter adapter =
//                new SimpleAdapter(this,websites,R.layout.list_raw,keys,ids);
//        websites.add(hash1);
//        websites.add(hash2);
//        ListView lv = findViewById(R.id.city_list);
//        lv.setAdapter(adapter);
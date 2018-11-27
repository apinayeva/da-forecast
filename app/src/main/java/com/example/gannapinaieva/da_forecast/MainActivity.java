package com.example.gannapinaieva.da_forecast;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing a new String Array
        String[] fruits = new String[]
        {
                "Cape Gooseberry",
                "Capuli cherry"
        };

        // Get reference of widgets from XML layout
        final ListView cityList = findViewById(R.id.city_list);

        // Create a List from String Array elements
        final List<String> fruits_list = new ArrayList<>(Arrays.asList(fruits));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, fruits_list);

        // DataBind ListView with items from ArrayAdapter
        cityList.setAdapter(arrayAdapter);


        Button selectCity = findViewById(R.id.find_button);
        selectCity.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                TextView randomText = findViewById(R.id.find_city_field);

                fruits_list.add(randomText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}
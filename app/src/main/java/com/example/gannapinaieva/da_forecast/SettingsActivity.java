package com.example.gannapinaieva.da_forecast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button removeAll, returnBack;
    Spinner dropdown;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dbHelper = new DBHelper(this);

        // Set data into drop down list
        dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Every 1 hour", "Every 6 hour", "Manual"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        initControl();
    }

    private void initControl() {
        removeAll = findViewById(R.id.deleteAllBtn);
        returnBack = findViewById(R.id.backBtn);

        removeAll.setOnClickListener(this);
        returnBack.setOnClickListener(this);
    }

    private void deleteAllCitiesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setMessage("Do you really want to delete all cities?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteAllCities();
                Toast.makeText(SettingsActivity.this, "All data is removed", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.deleteAllBtn:
                deleteAllCitiesDialog();
                break;

            case R.id.backBtn:
                Intent intent = new Intent(SettingsActivity.this, OpenWeatherMainActivity.class);
                startActivity(intent);
                break;
        }
    }
}

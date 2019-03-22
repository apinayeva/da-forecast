package com.example.gannapinaieva.da_forecast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class FirstLaunchSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnYahoo;
    private SharedPreferences prefs = null;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_launch_settings);

        prefs = getSharedPreferences("com.example.gannapinaieva.da_forecast_01", MODE_PRIVATE);

        findViewById(R.id.btnYahoo).setOnClickListener(this);
        findViewById(R.id.btnOpenWeather).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYahoo:
                Toast.makeText(FirstLaunchSettingsActivity.this, "btnYahoo clicked", Toast.LENGTH_LONG).show();
                prefs.edit().putBoolean("api", true);
                prefs.edit().putBoolean("firstrun", false).apply();
                break;
            case R.id.btnOpenWeather:
                Toast.makeText(FirstLaunchSettingsActivity.this, "btnOpenWeather clicked", Toast.LENGTH_LONG).show();
                prefs.edit().putBoolean("api", false);
                prefs.edit().putBoolean("firstrun", false).apply();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!prefs.getBoolean("firstrun", true)) {
            // todo: разобраться почему так логика работает странно
            if(!prefs.getBoolean("api",false)) {
                intent = new Intent(this, OpenWeatherMainActivity.class);
            }
            else {
//                Toast.makeText(FirstLaunchSettingsActivity.this, "api = yahoo and not implemented", Toast.LENGTH_LONG).show();
                intent = new Intent(this, YahooMainActivity.class);
            }
            startActivity(intent);
        }
    }
}
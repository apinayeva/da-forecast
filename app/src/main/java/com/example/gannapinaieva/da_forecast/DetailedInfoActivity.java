package com.example.gannapinaieva.da_forecast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class DetailedInfoActivity extends AppCompatActivity {

    TextView cityNameValue, res;

    private TextView descriptionValue, humidityValue, pressureValue, windSpeedValue, tempValue, tempMinValue, tempMaxValue;
    Button search;
    JSONObject data = null;
    String url;

    Button returnBackButton;

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";

    private String cityName;

    // todo: what is it?
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        cityNameValue = findViewById(R.id.cityname);
        descriptionValue = findViewById(R.id.descriptionValue);
        humidityValue = findViewById(R.id.humidityValue);
        pressureValue = findViewById(R.id.pressureValue);
        windSpeedValue = findViewById(R.id.windSpeedValue);
        tempValue = findViewById(R.id.tempValue);
        tempMinValue = findViewById(R.id.tempMinValue);
        tempMaxValue = findViewById(R.id.tempMaxValue);

        res = findViewById(R.id.res);

        // Get data from main activity
        Intent intent = getIntent();
        cityName = intent.getStringExtra("city_name");
        cityNameValue.setText(cityName);

        try {
            url = String.format(OPEN_WEATHER_MAP_API, cityName, this.getString(R.string.open_weather_maps_app_id));
            AsyncTask<String, Void, JSONObject> json = new GetWeatherTask(res).execute(url);
            descriptionValue.setText(json.get().getJSONArray("weather").getJSONObject(0).getString("description"));

            JSONObject main = json.get().getJSONObject("main");
            tempValue.setText(main.getString("temp") + " ℃");

            humidityValue.setText(main.getString("humidity") + "%");
            pressureValue.setText(main.getString("pressure") + " hPa");

            tempMinValue.setText(main.getString("temp_min") + " ℃");
            tempMaxValue.setText(main.getString("temp_max") + " ℃");

            windSpeedValue.setText(json.get().getJSONObject("wind").getString("speed"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Button for returning back to main page with list of cities
        returnBackButton = findViewById(R.id.returnBackButton);
        View.OnClickListener returnBackAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedInfoActivity.this, OpenWeatherMainActivity.class);
                startActivity(intent);
            }
        };
        returnBackButton.setOnClickListener(returnBackAction);
    }
}
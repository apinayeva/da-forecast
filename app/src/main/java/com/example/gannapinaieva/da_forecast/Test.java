package com.example.gannapinaieva.da_forecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class Test extends AppCompatActivity {

    TextView cityname;
    Button search;
    JSONObject data = null;

    Button returnBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        cityname = findViewById(R.id.cityname);

        // Get data from main activity
        Intent intent = getIntent();

        String fName = intent.getStringExtra("city_name");
        cityname.setText(fName);

        // Button for returning back to main page with list of cities
        returnBackButton = findViewById(R.id.returnBackButton);
        View.OnClickListener returnBackAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Test.this, MainActivity.class);
                startActivity(intent);
            }
        };
        returnBackButton.setOnClickListener(returnBackAction);
    }
}
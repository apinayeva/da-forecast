package com.example.gannapinaieva.da_forecast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test extends AppCompatActivity {

    TextView cityname;
    Button search;
    JSONObject data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        cityname = findViewById(R.id.cityname);
        search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityname.setText(getJSON("Sydney"));

            }
        });

        Intent intent = getIntent();

        String fName = intent.getStringExtra("city_name");
        cityname.setText(fName);
    }

    //groupId - идентификатор группы, частью которой является пункт меню
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // What menu is selected
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        StringBuilder sb = new StringBuilder();

        // Выведем в TextView информацию о нажатом пункте меню
        sb.append("Item Menu");
        sb.append("\r\n groupId: " + String.valueOf(item.getGroupId()));
        sb.append("\r\n itemId: " + String.valueOf(item.getItemId()));
        sb.append("\r\n order: " + String.valueOf(item.getOrder()));
        sb.append("\r\n title: " + item.getTitle());
        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    public String getJSON(final String city) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=5e94658bfd43bd6845b832732230d202");

                    Toast.makeText(Test.this, "" + url, Toast.LENGTH_LONG).show();

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());

                    if(data.getInt("cod") != 200) {
//                        System.out.println("Cancelled");
                        Toast.makeText(Test.this, "Cancelled", Toast.LENGTH_LONG).show();
                        return null;
                    }


                } catch (Exception e) {

                    System.out.println("Exception "+ e.getMessage());
                    return null;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                if(data!=null){
//                    Log.d("my weather received",data.toString());
                    Toast.makeText(Test.this, "my weather receive" + data.toString(), Toast.LENGTH_LONG).show();
//                    cityName.setText(data.toString());
                }

            }
        }.execute();
       return data.toString();
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(this, "MainActivity: onStart()", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this, "MainActivity: onResume()", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(this, "MainActivity: onPause()", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(this, "MainActivity: onStop()", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(this, "MainActivity: onDestroy()", Toast.LENGTH_LONG).show();
//    }
}
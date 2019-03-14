package com.example.gannapinaieva.da_forecast.Dialogs;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gannapinaieva.da_forecast.DBHelper;
import com.example.gannapinaieva.da_forecast.MainActivity;
import com.example.gannapinaieva.da_forecast.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddNewCityDialog extends DialogFragment implements OnClickListener {
    final String LOG_TAG = "myLogs";
    TextView cityName;
    Button findCityButton;

    DBHelper dbHelper;

    // For DB
    String tableName = "da_forecast";
    SQLiteDatabase db;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog1, null);
        findCityButton = v.findViewById(R.id.btnYes);
        findCityButton.setEnabled(false);
        v.findViewById(R.id.btnNo).setOnClickListener(this);
        v.findViewById(R.id.btnYes).setOnClickListener(this);
        v.findViewById(R.id.find_city_field);

        cityName = v.findViewById(R.id.find_city_field);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(getActivity());

        // If city is inserted then button is active
        cityName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    findCityButton.setEnabled(false);
                } else {
                    findCityButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                findCityButton.setEnabled(false);
            }
        });

        return v;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNo:
                Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());
                dismiss();
                break;
            case R.id.btnYes:
                String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=" + cityName.getText().toString() + "&units=metric&appid=cbed39654714639012ce07124b4bbd88&lang=ru");
                // TODO: убрать запись в сити нейм textview
                new GetWeatherTask(cityName).execute(url);
                break;
        }

    }

    // TODO: add case sensitive
    public boolean checkExistence(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "Select name from " + tableName + " where name = '" + name + "';";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    // TODO: не обрабатывает корректно если введено неверное значенип
    private class GetWeatherTask extends AsyncTask<String, Void, JSONObject> {
        private TextView textView;

        public GetWeatherTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject topLevel = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                topLevel = new JSONObject(builder.toString());

                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return topLevel;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            ContentValues cv = new ContentValues();
            db = dbHelper.getWritableDatabase();

            try {
                String cityName = json.getString("name");
                if (!checkExistence(cityName)) {
                    cv.put("name", cityName);
                    cv.put("temperature", json.getJSONObject("main").getDouble("temp"));
                } else {
                    Toast.makeText(getActivity(), cityName + " - city already exists!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            db.insert("da_forecast", null, cv);
            // Reload list in main activity
            ((MainActivity)getActivity()).printlistOfCities();
            cityName.setText("");

            dismiss();
        }
    }
}
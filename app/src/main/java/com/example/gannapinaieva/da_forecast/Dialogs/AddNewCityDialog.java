package com.example.gannapinaieva.da_forecast.Dialogs;

import android.app.DialogFragment;
import android.content.ContentValues;
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
import com.example.gannapinaieva.da_forecast.GetWeatherTask;
import com.example.gannapinaieva.da_forecast.OpenWeatherMainActivity;
import com.example.gannapinaieva.da_forecast.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AddNewCityDialog extends DialogFragment implements OnClickListener {
    final String LOG_TAG = "AddNewCityDialogLog";
    TextView cityName;
    Button findCityButton;

    DBHelper dbHelper;
    SQLiteDatabase db;

    private String dbName = "da_forecast";

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";

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
                String url = String.format(OPEN_WEATHER_MAP_API, cityName.getText().toString(), this.getString(R.string.open_weather_maps_app_id));
                // TODO: убрать запись в сити нейм textview

                ContentValues cv = new ContentValues();
                db = dbHelper.getWritableDatabase();

                try {
                    AsyncTask<String, Void, JSONObject> json = new GetWeatherTask(cityName).execute(url);
                    String cityName = json.get().getString("name");
                    if (!dbHelper.checkExistence(cityName)) {
                        cv.put("name", cityName);
                        cv.put("temperature", json.get().getJSONObject("main").getDouble("temp"));
                    } else {
                        Toast.makeText(getActivity(), cityName + " - city already exists!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                db.insert(dbName, null, cv);
                // Reload list in main activity
                ((OpenWeatherMainActivity)getActivity()).printlistOfCities();
                cityName.setText("");

                dismiss();
                break;
        }

    }
}
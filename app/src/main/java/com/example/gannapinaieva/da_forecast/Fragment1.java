package com.example.gannapinaieva.da_forecast;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

// https://startandroid.ru/ru/uroki/vse-uroki-spiskom/179-urok-109-android-3-fragments-listfragment-spisok.html

public class Fragment1 extends ListFragment {

    DBHelper dbHelper;
    ArrayList<Record> listOfCities;

    RecordAdapter recordAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DBHelper(getActivity());
        recordAdapter = new RecordAdapter(getActivity(), dbHelper.getCityForecastList());
        setListAdapter(recordAdapter);
        recordAdapter.notifyDataSetChanged();
    }

}
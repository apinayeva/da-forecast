package com.example.gannapinaieva.da_forecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.gannapinaieva.da_forecast.Dialogs.AddNewCityDialog;
import com.example.gannapinaieva.da_forecast.Dialogs.DeleteSelectedDialog;

import java.util.ArrayList;

public class OpenWeatherMainActivity extends AppCompatActivity { //implements OnClickListener {

    // TODO: if empty list then ask location
    // TODO: lowercase in checking city doesn't work

    // if extends Activity - than full screen
    private ListView cityList;
    private LinearLayout listOfSelectedCities;

    RecordAdapter recordAdapter;

    DBHelper dbHelper;
    ArrayList<Record> listOfCities;


//    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        prefs = getSharedPreferences("com.example.gannapinaieva.da_forecast", MODE_PRIVATE);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        listOfSelectedCities = findViewById(R.id.listOfCities);

        // Get reference of widgets from XML layout
        // TODO: what is it?
        cityList = findViewById(R.id.city_list);

        // Fill list with cities
        printlistOfCities();

//        // Long click on item will cause opening dialog to delete this item or not
//        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                deleteCityDialog(((Record) parent.getItemAtPosition(position)).getName());
//                printlistOfCities();
//                return true;
//            }
//
//        });
    }

//    public void deleteCityDialog(final String cityName) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setMessage("Do you really want to delete " + cityName + " city?");
//
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                deleteCity(cityName);
//                cityList.invalidateViews();
//                dialog.dismiss();
//            }
//        });
//
//        builder.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.show();
//    }

    //groupId - идентификатор группы, частью которой является пункт меню
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_add:
                new AddNewCityDialog().show(getFragmentManager(), "addCityDialog");
                return true;
            case R.id.menu_delete:
                DeleteSelectedDialog dialogFragment = new DeleteSelectedDialog();
                Bundle bundle = new Bundle();
                bundle.putString("list", (new RecordAdapter(this, dbHelper.getCityForecastList()).showResult()));
                dialogFragment.setArguments(bundle);
                dialogFragment.show((OpenWeatherMainActivity.this).getSupportFragmentManager(),"Image Dialog");
                return true;
            case R.id.menu_settings:
//                deleteAllCitiesDialog();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_exit:
                System.exit(1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: remove it
    public void printlistOfCities() {
        // Fill list with data from DB
        recordAdapter = new RecordAdapter(this, dbHelper.getCityForecastList());
        cityList.setAdapter(recordAdapter);
        recordAdapter.notifyDataSetChanged();
        cityList.invalidateViews();
    }
}
package com.example.gannapinaieva.da_forecast;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
https://medium.com/@sankalpchauhan.me/creating-a-custom-arrayadapter-for-a-user-defined-arraylist-in-android-e076d0064d0d
 */
public class RecordAdapter extends BaseAdapter {//ArrayAdapter<Record> {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Record> objects;

    String res;

    public RecordAdapter(Context context, ArrayList<Record> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Record getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // todo: why final?
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView= LayoutInflater.from(ctx).inflate(R.layout.item, parent,false);
        }
        Record getPos = getItem(position);
        ((TextView) listItemView.findViewById(R.id.name)).setText(getPos.name);
        ((TextView) listItemView.findViewById(R.id.temp)).setText(getPos.temperature);

        CheckBox checkBox = listItemView.findViewById(R.id.selected);
        checkBox.setChecked(false);

        // присваиваем чекбоксу обработчик
        checkBox.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        checkBox.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        checkBox.setChecked(getPos.box);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();


//                Toast.makeText(ctx, showResult(), Toast.LENGTH_LONG).show();
            }
        });


        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, DetailedInfoActivity.class);
                intent.putExtra("city_name", getItem(position).name);
                ctx.startActivity(intent);
            }
        });

//        listItemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
////                Toast.makeText(ctx, getItem(position).name, Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(ctx, Test.class);
//                intent.putExtra("city_name", getItem(position).name);//parent.getItemAtPosition(position).toString());
//                ctx.startActivity(intent);
////                deleteCityDialog(((Record) parent.getItemAtPosition(position)).getName());
////                printlistOfCities();
//                return true;
//            }
//        });

        return listItemView;
    }

    Record getRecord(int position) {
        return ((Record) getItem(position));
    }

    ArrayList<Record> getBox() {
        ArrayList<Record> box = new ArrayList<Record>();
        for (Record p : objects) {
            // если в корзине
            if (p.box)
                box.add(p);
        }
        return box;
    }

    public String showResult() {
        List<String> res = null;
        String result = null;
        for (Record p : getBox()) {
            if (p.box)
                result += p.name;
//                res.add(p.name);
        }
        return result;
    }

    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getRecord((Integer) buttonView.getTag()).box = isChecked;
        }
    };
}

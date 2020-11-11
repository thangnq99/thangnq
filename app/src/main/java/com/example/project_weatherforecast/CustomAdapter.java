package com.example.project_weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Custom> arrayList;

    public CustomAdapter(Context context, ArrayList<Custom> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listviewline,null);
        Custom custom = arrayList.get(position);

        TextView textDay = (TextView) convertView.findViewById(R.id.textViewDate);
        TextView textStatus = (TextView) convertView.findViewById(R.id.type);
        TextView textMaxTemp = (TextView) convertView.findViewById(R.id.txtTempMax);
        TextView textMinTemp = (TextView) convertView.findViewById(R.id.txtTempMin);
        ImageView imageStatus = (ImageView) convertView.findViewById(R.id.imgType);

        textDay.setText(custom.Day);
        textStatus.setText(custom.Status);
        textMaxTemp.setText(custom.MaxTemp + " °C");
        textMinTemp.setText(custom.MinTemp + " °C");
        Picasso.with(context).load("http://openweathermap.org/img/w/"+custom.Image+".png").into(imageStatus);
        return convertView;
    }
}

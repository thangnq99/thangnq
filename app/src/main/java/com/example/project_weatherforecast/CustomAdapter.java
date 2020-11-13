package com.example.project_weatherforecast;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        LinearLayout linearLayout = convertView.findViewById(R.id.line);
//        linearLayout.setBackground(context.getResources().getDrawable(R.drawable.cloudnight));
        LinearLayout linearLayout1 = convertView.findViewById(R.id.line3);
        textDay.setText(custom.Day);
        textStatus.setText(custom.Status);
        textMaxTemp.setText(custom.MaxTemp + " °C");
        textMinTemp.setText(custom.MinTemp + " °C");
        Picasso.with(context).load("http://openweathermap.org/img/w/"+custom.Image+".png").into(imageStatus);
        switch (custom.Image){
            case "01d":
                linearLayout.setBackgroundResource(R.drawable.cloudday);
//                linearLayout1.setBackgroundResource(R.drawable.cloudday);
                break;
            case "01n":

//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                linearLayout.setBackgroundResource(R.drawable.cloudnight);
//                linearLayout1.setBackgroundResource(R.drawable.cloudnight);
//                linearLayout.setBackgroundColor(Color.RED);

                break;
            case "02d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudday));
                linearLayout.setBackgroundResource(R.drawable.cloudday);
//                linearLayout1.setBackgroundResource(R.drawable.cloudday);
//                linearLayout.setBackgroundColor(Color.RED);
                break;
            case "02n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                linearLayout.setBackgroundResource(R.drawable.cloudnight);
//                linearLayout1.setBackgroundResource(R.drawable.cloudnight);
//                linearLayout.setBackgroundColor(Color.RED);
                break;
            case "03d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.scatteredcloud));
                linearLayout.setBackgroundResource(R.drawable.scatteredcloud);
//                linearLayout1.setBackgroundResource(R.drawable.scatteredcloud);
//                linearLayout.setBackgroundColor(Color.RED);
                break;
            case "03n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                linearLayout.setBackgroundResource(R.drawable.cloudnight);
//                linearLayout1.setBackgroundResource(R.drawable.cloudnight);
                break;
            case "04d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.brokenclouds));
                linearLayout.setBackgroundResource(R.drawable.brokenclouds);
//                linearLayout1.setBackgroundResource(R.drawable.brokenclouds);
                break;
            case "04n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                linearLayout.setBackgroundResource(R.drawable.cloudnight);
//                linearLayout1.setBackgroundResource(R.drawable.cloudnight);
                break;
            case "09d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.showerrain));
                linearLayout.setBackgroundResource(R.drawable.showerrain);
//                linearLayout1.setBackgroundResource(R.drawable.showerrain);
//                linearLayout.setBackgroundColor(Color.RED);
                break;
            case "09n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.showerrain));
                linearLayout.setBackgroundResource(R.drawable.showerrain);
//                linearLayout1.setBackgroundResource(R.drawable.showerrain);
                break;
            case "10d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rainday));
                linearLayout.setBackgroundResource(R.drawable.rainday);
//                linearLayout1.setBackgroundResource(R.drawable.rainday);
                break;
            case "10n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rainnight));
                linearLayout.setBackgroundResource(R.drawable.rainnight);
//                linearLayout1.setBackgroundResource(R.drawable.rainnight);
                break;
            case "11d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.thunderstorm));
                linearLayout.setBackgroundResource(R.drawable.thunderstorm);
//                linearLayout1.setBackgroundResource(R.drawable.thunderstorm);
                break;
            case "11n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.thunderstorm));
                linearLayout.setBackgroundResource(R.drawable.thunderstorm);
//                linearLayout1.setBackgroundResource(R.drawable.thunderstorm);
                break;
            case "13d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.snowday));
                linearLayout.setBackgroundResource(R.drawable.snowday);
//                linearLayout1.setBackgroundResource(R.drawable.snowday);
                break;
            case "13n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.snownight));
                linearLayout.setBackgroundResource(R.drawable.snownight);
//                linearLayout1.setBackgroundResource(R.drawable.snownight);
                break;
            case "50d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.mistday));
                linearLayout.setBackgroundResource(R.drawable.mistday);
//                linearLayout1.setBackgroundResource(R.drawable.mistday);
                break;
            case "50n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.mistnight));
                linearLayout.setBackgroundResource(R.drawable.mistnight);
//                linearLayout1.setBackgroundResource(R.drawable.mistnight);
                break;
        }
        return convertView;
    }

}

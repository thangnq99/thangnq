package com.example.project_weatherforecast;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DayOneWeek extends AppCompatActivity {
    String City = "";
    ImageView imageBack;
    TextView txtName;
    ListView listView;
    CustomAdapter customAdapter;
    ArrayList<Custom> arrayWeather;
    String icon2 = "01d";
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_one_week);
        linearLayout = findViewById(R.id.lLayout);
        Mapping();
        Intent intent = getIntent();
        String city = intent.getStringExtra("Name");
        icon2 = intent.getStringExtra("Icon");
        Log.d("KetQua","Du lieu chuyen qua" + city);
        if(city.equals("")) {
            City = "Hanoi";
            GetDayOnWeek(City);
            setImage(icon2);
        }else {
            City = city;
            GetDayOnWeek(City);
            setImage(icon2);
        }
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Mapping() {
        imageBack = (ImageView) findViewById(R.id.imgBack);
        txtName = (TextView) findViewById(R.id.nameCity);
        listView = (ListView) findViewById(R.id.listView);
        arrayWeather = new ArrayList<Custom>();
        customAdapter = new CustomAdapter(DayOneWeek.this,arrayWeather);
        listView.setAdapter(customAdapter);
    }

    private void GetDayOnWeek(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+"&units=metric&cnt=7&appid=53fbf527d52d4d773e828243b90c1f8e";
        RequestQueue requestQueue = Volley.newRequestQueue(DayOneWeek.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            txtName.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String day = jsonObjectList.getString("dt");
                                long l = Long.valueOf(day);
                                Date date = new Date(l*1000L);
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String Day = new SimpleDateFormat("E yyyy-MM-dd", Locale.ENGLISH).format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                                String max = jsonObjectTemp.getString("max");
                                String min = jsonObjectTemp.getString("min");

                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);

                                String MaxC = String.valueOf(a.intValue());
                                String MinC = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                arrayWeather.add(new Custom(Day,status,icon,MaxC,MinC));
                            }
                            customAdapter.notifyDataSetChanged();

                        }catch (JSONException e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    private void setImage(final String value){
                switch (value){
                    case "01d":

                        linearLayout.setBackgroundResource(R.drawable.cloudday);
                        break;
                    case "01n":

//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                        linearLayout.setBackgroundResource(R.drawable.cloudnight);
                        break;
                    case "02d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudday));
                        linearLayout.setBackgroundResource(R.drawable.cloudday);

                        break;
                    case "02n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                        linearLayout.setBackgroundResource(R.drawable.cloudnight);
                        break;
                    case "03d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.scatteredcloud));
                        linearLayout.setBackgroundResource(R.drawable.scatteredcloud);
                        break;
                    case "03n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                        linearLayout.setBackgroundResource(R.drawable.cloudnight);
                        break;
                    case "04d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.brokenclouds));
                        linearLayout.setBackgroundResource(R.drawable.brokenclouds);
                        break;
                    case "04n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                        linearLayout.setBackgroundResource(R.drawable.cloudnight);
                        break;
                    case "09d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.showerrain));
                        linearLayout.setBackgroundResource(R.drawable.showerrain);
                        break;
                    case "09n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.showerrain));
                        linearLayout.setBackgroundResource(R.drawable.showerrain);
                        break;
                    case "10d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rainday));
                        linearLayout.setBackgroundResource(R.drawable.rainday);
                        break;
                    case "10n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rainnight));
                        linearLayout.setBackgroundResource(R.drawable.rainnight);
                        break;
                    case "11d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.thunderstorm));
                        linearLayout.setBackgroundResource(R.drawable.thunderstorm);
                        break;
                    case "11n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.thunderstorm));
                        linearLayout.setBackgroundResource(R.drawable.thunderstorm);
                        break;
                    case "13d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.snowday));
                        linearLayout.setBackgroundResource(R.drawable.snowday);
                        break;
                    case "13n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.snownight));
                        linearLayout.setBackgroundResource(R.drawable.snownight);
                        break;
                    case "50d":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.mistday));
                        linearLayout.setBackgroundResource(R.drawable.mistday);
                        break;
                    case "50n":
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.mistnight));
                        linearLayout.setBackgroundResource(R.drawable.mistnight);
                        break;
                }
    }
}
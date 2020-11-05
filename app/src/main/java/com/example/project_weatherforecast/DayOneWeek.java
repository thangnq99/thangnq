package com.example.project_weatherforecast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_one_week);
        Mapping();
        Intent intent = getIntent();
        String city = intent.getStringExtra("Name");
        Log.d("KetQua","Du lieu chuyen qua" + city);
        if(city.equals("")) {
            City = "Hanoi";
            GetDayOnWeek(City);
        }else {
            City = city;
            GetDayOnWeek(City);
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
                                String Day = new SimpleDateFormat("EEEE yyyy-MM-dd", Locale.ENGLISH).format(date);

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
}
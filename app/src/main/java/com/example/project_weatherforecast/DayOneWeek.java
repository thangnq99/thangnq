package com.example.project_weatherforecast;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DayOneWeek extends AppCompatActivity {
    String City = "";
    TextView txtName;
    ListView listView;
    CustomAdapter customAdapter;
    ArrayList<Custom> arrayWeather;
    String icon2 = "01d";
    LinearLayout linearLayout;
    int index = 0;
    public  static int weatherItem;
    TextView txtTemp,txtStatus,txtHumidity,txtCloud,txtWind;
    String API = "53fbf527d52d4d773e828243b90c1f8e";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_one_week);
        linearLayout = findViewById(R.id.lLayout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Mapping();
        Intent intent = getIntent();
        String city = intent.getStringExtra("Name");
        icon2 = intent.getStringExtra("Icon");
        if(city.equals("")) {
            City = "Hanoi";
            new weather7DayTask().execute();
        }else {
            City = city;
            new weather7DayTask().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        weatherItem = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void Mapping() {

        txtStatus = findViewById(R.id.txtStatus);
        txtName = (TextView) findViewById(R.id.nameCity);
        listView = (ListView) findViewById(R.id.listView);
        arrayWeather = new ArrayList<Custom>();
        customAdapter = new CustomAdapter(DayOneWeek.this,arrayWeather);
        listView.setAdapter(customAdapter);
    }

    class weather7DayTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return HttpRequest.excuteGet("http://api.openweathermap.org/data/2.5/forecast/daily?q="+City+"&units=metric&cnt=17&appid=53fbf527d52d4d773e828243b90c1f8e");
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                String name = jsonObjectCity.getString("name");
                txtName.setText("City: " +name +","+jsonObjectCity.getString("country"));
//                txtName.setText("City: " +name);

                JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArrayList.length(); i++) {
                    JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                    String day = jsonObjectList.getString("dt");
                    long l = Long.valueOf(day);
                    Date date = new Date(l*1000L);
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                    String Day = new SimpleDateFormat("E dd/MM/yyyy", Locale.ENGLISH).format(date);

                    JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                    String max = jsonObjectTemp.getString("max");
                    String min = jsonObjectTemp.getString("min");

                    Double a = Double.valueOf(max);
                    Double b = Double.valueOf(min);
                    String MaxC = String.valueOf(b.intValue());
                    String MinC = String.valueOf(a.intValue());

                    JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String status = jsonObjectWeather.getString("description");
                    String icon = jsonObjectWeather.getString("icon");
                    arrayWeather.add(new Custom(Day,status,icon,MaxC,MinC));

                }
                customAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
//    private void setImage(final String value, int i){
//                switch (value){
//                    case "01d":
//                        listView.setBackgroundResource(R.drawable.cloudday);
//                        break;
//                    case "01n":
//
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
//                        listView.setBackgroundResource(R.drawable.cloudnight);
//
//                        break;
//                    case "02d":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudday));
//                        listView.setBackgroundResource(R.drawable.cloudday);
//
//                        break;
//                    case "02n":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
//                        listView.setBackgroundResource(R.drawable.cloudnight);
//                        break;
//                    case "03d":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.scatteredcloud));
//                        listView.setBackgroundResource(R.drawable.scatteredcloud);
//                        break;
//                    case "03n":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
//                        listView.setBackgroundResource(R.drawable.cloudnight);
//                        break;
//                    case "04d":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.brokenclouds));
//                        listView.setBackgroundResource(R.drawable.brokenclouds);
//                        break;
//                    case "04n":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.cloudnight));
//                        listView.setBackgroundResource(R.drawable.cloudnight);
//                        break;
//                    case "09d":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.showerrain));
//                        listView.setBackgroundResource(R.drawable.showerrain);
//                        break;
//                    case "09n":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.showerrain));
//                        listView.setBackgroundResource(R.drawable.showerrain);
//                        break;
//                    case "10d":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rainday));
//                        listView.setBackgroundResource(R.drawable.rainday);
//                        break;
//                    case "10n":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rainnight));
//                        listView.setBackgroundResource(R.drawable.rainnight);
//                        break;
//                    case "11d":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.thunderstorm));
//                        listView.setBackgroundResource(R.drawable.thunderstorm);
//                        break;
//                    case "11n":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.thunderstorm));
//                        listView.setBackgroundResource(R.drawable.thunderstorm);
//                        break;
//                    case "13d":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.snowday));
//                        listView.setBackgroundResource(R.drawable.snowday);
//                        break;
//                    case "13n":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.snownight));
//                        listView.setBackgroundResource(R.drawable.snownight);
//                        break;
//                    case "50d":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.mistday));
//                        listView.setBackgroundResource(R.drawable.mistday);
//                        break;
//                    case "50n":
////                        linearLayout.setBackground(getResources().getDrawable(R.drawable.mistnight));
//                        listView.setBackgroundResource(R.drawable.mistnight);
//                        break;
//                }
//    }
}
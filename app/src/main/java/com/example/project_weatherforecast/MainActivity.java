package com.example.project_weatherforecast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.res.AssetManager;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

//     String API = "5f0e7bb0ea868b77d281b07c046f565c";
    String API = "53fbf527d52d4d773e828243b90c1f8e";
     String city = "Hanoi";
     TextView address, status, recentTemp, tempMax, tempMin, date, description, sunSet, sunRise,
                    windSpeed, pressure, humidity, feelsLike;

     ImageView image, sunSetImg, sunRiseImg, windImg, pressureImg, humidityImg, feelImg;
     ImageButton btnSearch;
     AutoCompleteTextView etSearch;
     View mainView;
     Button btDayOnWeek;
     String icons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //
        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();

        address = findViewById(R.id.txtAddress);
        status = findViewById(R.id.txtStatus);
        recentTemp = findViewById(R.id.txtRecentTemp);
        tempMax = findViewById(R.id.txtTempMax);
        tempMin = findViewById(R.id.txtTempMin);
        date = findViewById(R.id.txtDate);
        description = findViewById(R.id.txtDescription);
        image = findViewById(R.id.weatherIcon);
        mainView = findViewById(R.id.mainView);
        sunSet = findViewById(R.id.txtSunset);
        sunRise = findViewById(R.id.txtSunrise);
        windSpeed = findViewById(R.id.txtWindSpeed);
        pressure = findViewById(R.id.txtPressure);
        humidity = findViewById(R.id.txtHumidity);
        feelsLike = findViewById(R.id.txtFeelsLike);
        feelImg = findViewById(R.id.feelImg);
        sunRiseImg = findViewById(R.id.sunriseImg);
        sunSetImg = findViewById(R.id.sunsetImg);
        windImg = findViewById(R.id.windImg);
        pressureImg = findViewById(R.id.pressureImg);
        humidityImg = findViewById(R.id.humidityImg);


        sunRiseImg.setImageDrawable(getResources().getDrawable(R.drawable.iconsunrise));
        sunSetImg.setImageDrawable(getResources().getDrawable(R.drawable.iconsunset));
        pressureImg.setImageDrawable(getResources().getDrawable(R.drawable.iconpressure));
        humidityImg.setImageDrawable(getResources().getDrawable(R.drawable.iconhumidity));
        windImg.setImageDrawable(getResources().getDrawable(R.drawable.windspeed));

        new weatherTask().execute();

        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);

        List<String> ArrayCity = ArrayCity();
        String [] listCity = new String[ArrayCity.size()];
        ArrayCity.toArray(listCity);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayCity);
        etSearch.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(),0);
                city = String.valueOf(etSearch.getText());
                new weatherTask().execute();
                mainView.requestFocus();
                hideKeyboard(MainActivity.this);
            }
        });

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode){
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            city = String.valueOf(etSearch.getText());
                            new weatherTask().execute();
                            mainView.requestFocus();
                            hideKeyboard(MainActivity.this);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
       btDayOnWeek = findViewById(R.id.btDayOneWeek);
        btDayOnWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DayOneWeek.class);
                city = etSearch.getText().toString();
                intent.putExtra("Name",city);
                intent.putExtra("Icon",icons);
                startActivity(intent);
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public List<String> ArrayCity(){
        List<String> listCity = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(loadJsonFromAsset());
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                listCity.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return listCity;
    }

    public String loadJsonFromAsset(){
        String json = "";
        try {
            InputStream is = getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    class weatherTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return HttpRequest.excuteGet("http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid="+API);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject main = jsonObject.getJSONObject("main");
                JSONObject sys = jsonObject.getJSONObject("sys");
                JSONObject wind = jsonObject.getJSONObject("wind");
                JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);


                String location = jsonObject.getString("name") + ", " + sys.getString("country");
                String shortDes = weather.getString("description");

                String txtDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(new Date(jsonObject.getLong("dt") * 1000)) +"";
                String icon = weather.getString("icon");
                icons = icon;
                setImage(image, icon);

                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberFormat.setMaximumFractionDigits(1);
                String txtTemp = numberFormat.format(main.getDouble("temp")) +"°C";
                String txtTempMax = numberFormat.format(main.getDouble("temp_max")) +"°C";
                String txtTempMin = numberFormat.format(main.getDouble("temp_min")) +"°C";
                String txtFeelLike = numberFormat.format(main.getDouble("feels_like")) + "°C";

                String descriptionDetail = "Today: " + shortDes +" conditions with a heat index of " +txtFeelLike +". The high will be " + txtTempMax +".";
                if(main.getDouble("feels_like") < 15){
                    feelImg.setImageDrawable(getResources().getDrawable(R.drawable.feelcold));
                }else if (main.getDouble("feels_like") > 30){
                    feelImg.setImageDrawable(getResources().getDrawable(R.drawable.feelhot));
                }else{
                    feelImg.setImageDrawable(getResources().getDrawable(R.drawable.happiness));
                }

                String txtPressure = main.getString("pressure") + "hPa";
                String txtHumidity = main.getString("humidity") + "%";
                String txtWind = wind.getString("speed") + "km/h";
                String txtSunrise = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(new Date(sys.getLong("sunrise") * 1000));
                String txtSunset = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(new Date(sys.getLong("sunset") * 1000));

                address.setText(location);
                status.setText(shortDes);
                date.setText(txtDate);
                recentTemp.setText(txtTemp);
                tempMax.setText("Max: "+txtTempMax);
                tempMin.setText("Min: "+txtTempMin);
                description.setText(descriptionDetail);
                sunRise.setText(txtSunrise);
                sunSet.setText(txtSunset);
                pressure.setText(txtPressure);
                humidity.setText(txtHumidity);
                windSpeed.setText(txtWind);
                feelsLike.setText(txtFeelLike);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void setImage(final ImageView imageView, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (value){
                    case "01d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i01d));
                        mainView.setBackground(getResources().getDrawable(R.drawable.cloudday));
                        break;
                    case "01n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i01n));
                        mainView.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                        break;
                    case "02d":
                        mainView.setBackground(getResources().getDrawable(R.drawable.cloudday));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i02d));
                        break;
                    case "02n":
                        mainView.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i02n));
                        break;
                    case "03d":
                        mainView.setBackground(getResources().getDrawable(R.drawable.scatteredcloud));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i03d));
                        break;
                    case "03n":
                        mainView.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i03d));
                        break;
                    case "04d":
                        mainView.setBackground(getResources().getDrawable(R.drawable.brokenclouds));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i04d));
                        break;
                    case "04n":
                        mainView.setBackground(getResources().getDrawable(R.drawable.cloudnight));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i04d));
                        break;
                    case "09d":
                        mainView.setBackground(getResources().getDrawable(R.drawable.showerrain));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i09d));
                        break;
                    case "09n":
                        mainView.setBackground(getResources().getDrawable(R.drawable.showerrain));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i09n));
                        break;
                    case "10d":
                        mainView.setBackground(getResources().getDrawable(R.drawable.rainday));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i10d));
                        break;
                    case "10n":
                        mainView.setBackground(getResources().getDrawable(R.drawable.rainnight));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i10n));
                        break;
                    case "11d":
                        mainView.setBackground(getResources().getDrawable(R.drawable.thunderstorm));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i11d));
                        break;
                    case "11n":
                        mainView.setBackground(getResources().getDrawable(R.drawable.thunderstorm));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i11n));
                        break;
                    case "13d":
                        mainView.setBackground(getResources().getDrawable(R.drawable.snowday));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i13d));
                        break;
                    case "13n":
                        mainView.setBackground(getResources().getDrawable(R.drawable.snownight));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i13n));
                        break;
                    case "50d":
                        mainView.setBackground(getResources().getDrawable(R.drawable.mistday));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i50d));
                        break;
                    case "50n":
                        mainView.setBackground(getResources().getDrawable(R.drawable.mistnight));
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.i50n));
                        break;
                }
            }
        });
    }
}
package com.example.weather_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView header;
    TextView errorMsg;
    EditText location;

    RecyclerView recyclerView;
    RecyclerWeatherAdapter recyclerWeatherAdapter;
    ArrayList<Weather> weatherArrayList = new ArrayList<>();
    final String apikey = "b9c730b56fe860d9071a6ee0324ece11";
    final String url = "https://api.openweathermap.org/data/2.5/forecast";
    DecimalFormat df = new DecimalFormat("#.##");
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager( MainActivity.this,LinearLayoutManager.HORIZONTAL, false));

        location = findViewById(R.id.city);



        errorMsg = findViewById(R.id.errorMsg);
        header = findViewById(R.id.header);



    }

    public void readingData(String url){
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("RESPONSE", jsonObject.toString());
                        //parsing
                        try {
                            errorMsg.setText("");
                            JSONArray weatherArray = jsonObject.getJSONArray("list");
                            weatherArrayList.clear();
                            for(int i=0; i< weatherArray.length(); ++i){
                                JSONObject weatherObject = weatherArray.getJSONObject(i);
                                JSONObject mainObject = weatherObject.getJSONObject("main");
                                double temp = mainObject.getDouble("temp")- 273.15;
                                String tempInString = df.format(temp) + "°";
                                JSONArray weatherList = weatherObject.getJSONArray("weather");
                                JSONObject weatherListObject = weatherList.getJSONObject(0);
                                String main = weatherListObject.getString("main");
                                String time = weatherObject.getString("dt_txt");


                                int image = setImage(main);
                                weatherArrayList.add(new Weather(tempInString, main, time, image));

                            }

                            recyclerWeatherAdapter = new RecyclerWeatherAdapter(MainActivity.this
                            , weatherArrayList);
                            recyclerView.setAdapter(recyclerWeatherAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", anError.toString());
                        errorMsg.setText("Please check the spelling");
                    }
                });
    }

    public int setImage(String main) {
        if(main.equals("Clouds")){
            return R.drawable.ic_clouds;
        }
        else if(main.equals("Clear")){
            return R.drawable.ic_clear;
        }
        else if(main.equals("Rain")){
            return  R.drawable.ic_rain;
        }
        else if(main.equals("Thunderstorm")){
            return R.drawable.ic_thunderstorm;
        }
        else if(main.equals("Snow")){
            return  R.drawable.ic_snow;

        }
        else if(main.equals("Cloud_wind")){
            return R.drawable.ic_cloud_wind;
        }
        else if(main.equals("Drizzle")){
            return R.drawable.ic_drizzle;
        }
        else if(main.equals("Night_rainy")){
            return R.drawable.ic_night_rainy;
        }
        else if(main.equals("Tonado")){
            return R.drawable.ic_tonado;
        }
        else if(main.equals("Haze")){
            return R.drawable.ic_haze_fog;
        }
        else{
            return R.drawable.ic_preview;
        }
    }

    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String city = location.getText().toString().trim();


        if(city.equals("")){
            errorMsg.setText("You must enter a city name");
        }
        else{

            try {
                errorMsg.setText("");
                tempUrl = url + "?q=" + city + "&appid=" + apikey;
                readingData(tempUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
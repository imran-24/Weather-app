package com.example.weather_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView weather, header,Date;
    TextView temperature, errorMsg;
    EditText location;
    ImageView imageView;
    String date = "";
    final String apikey = "b9c730b56fe860d9071a6ee0324ece11";
    final String url = "https://api.openweathermap.org/data/2.5/weather";
    DecimalFormat df = new DecimalFormat("#.##");
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weather = findViewById(R.id.weather);
        location = findViewById(R.id.city);
        temperature = findViewById(R.id.temperature);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.ic_preview);
        errorMsg = findViewById(R.id.errorMsg);
        header = findViewById(R.id.header);
        Date = findViewById(R.id.date);
        getDate();

    }

    public void readingData(String url){
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("RESPONSE", jsonObject.toString());
                        //parsing
                        try {
                            errorMsg.setText("");
                            JSONArray weatherArray = jsonObject.getJSONArray("weather");
                            String nameOfCity = jsonObject.getString("name");
                            JSONObject weatherObject = weatherArray.getJSONObject(0);
                            String main = weatherObject.getString("main").trim();
                            System.out.println(main);

                            setImage(main);
                            JSONObject mainObject = jsonObject.getJSONObject("main");
                            double temp = mainObject.getDouble("temp")- 273.15;
                            String tempInString = df.format(temp) + "°";
                            temperature.setText(tempInString);
                            weather.setText(main);
                            Date.setText(date);
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDate(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
        date = myDateObj.format(myFormatObj);

    }
    public void setImage(String main) {
        if(main.equals("Clouds")){
            imageView.setImageResource(R.drawable.ic_clouds);
            header.setBackgroundResource(R.color.lightSkyBlue);
        }
        else if(main.equals("Clear")){
            imageView.setImageResource(R.drawable.ic_clear);
            header.setBackgroundResource(R.color.lightYellow);
        }
        else if(main.equals("Rain")){
            imageView.setImageResource(R.drawable.ic_rain);
            header.setBackgroundResource(R.color.lightSkyBlue);
        }
        else if(main.equals("Thunderstorm")){
            imageView.setImageResource(R.drawable.ic_thunderstorm);
            header.setBackgroundResource(R.color.lightSkyBlue);
        }
        else if(main.equals("Snow")){
            imageView.setImageResource(R.drawable.ic_snow);

        }
        else if(main.equals("Cloud_wind")){
            imageView.setImageResource(R.drawable.ic_cloud_wind);
            header.setBackgroundResource(R.color.lightSkyBlue);
        }
        else if(main.equals("Drizzle")){
            imageView.setImageResource(R.drawable.ic_drizzle);
            header.setBackgroundResource(R.color.lightSkyBlue);
        }
        else if(main.equals("Night_rainy")){
            imageView.setImageResource(R.drawable.ic_night_rainy);
        }
        else if(main.equals("Tonado")){
            imageView.setImageResource(R.drawable.ic_tonado);
        }
        else if(main.equals("Haze")){
            imageView.setImageResource(R.drawable.ic_haze_fog);
            header.setBackgroundResource(R.color.lightSkyBlue);
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
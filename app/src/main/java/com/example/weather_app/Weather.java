package com.example.weather_app;

public  class Weather {
    String temp,weather,date;
    int image;

    public String getTemp() {
        return temp;
    }

    public String getWeather() {
        return weather;
    }

    public String getDate() {
        return date;
    }

    public int getImage() {
        return image;
    }

    public Weather(String temp, String weather, String date, int image) {
        this.temp = temp;
        this.weather = weather;
        this.date = date;
        this.image = image;
    }
}

package com.github.vaseghifard.weatherapplication.utils;

import com.github.vaseghifard.weatherapplication.rest.Endpoints;
import com.github.vaseghifard.weatherapplication.rest.RetrofitServiceGenerator;

public class Constants {
    public static String BASE_URL = "http://api.openweathermap.org/data/2.5/" ;
   // public static String IMAGEURL = "http://openweathermap.org/img/wn/" ;
    public static String appId="aa2a71732ae41d87675d7ffe3b1c3102";
    public static int count=37;
    public static int count_current=9;
    public static Endpoints endpoints = RetrofitServiceGenerator.createService(Endpoints.class) ;
}


//http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&appid=aa2a71732ae41d87675d7ffe3b1c3102
//https://samples.openweathermap.org/data/2.5/forecast/daily?lat=35.7196486&lon=51.4880871&cnt=10&appid=b1b15e88fa797225412429c1c50c122a1
//https://samples.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22
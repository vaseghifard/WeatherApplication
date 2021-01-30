package com.github.vaseghifard.weatherapplication.utils;

import com.github.vaseghifard.weatherapplication.rest.Endpoints;
import com.github.vaseghifard.weatherapplication.rest.RetrofitServiceGenerator;

public class Constants {
    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/" ;
    public static String appId="aa2a71732ae41d87675d7ffe3b1c3102";
    public static Endpoints endpoints = RetrofitServiceGenerator.createService(Endpoints.class) ;
}
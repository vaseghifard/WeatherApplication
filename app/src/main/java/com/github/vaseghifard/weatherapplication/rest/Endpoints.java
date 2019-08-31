package com.github.vaseghifard.weatherapplication.rest;

import android.util.Log;

import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;
import com.github.vaseghifard.weatherapplication.models.forecastWaetherResponse.ForecastWeathearResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Endpoints {
    @GET("weather")
    Call<CurrentWeatherResponseModel> getCurrentWeather(
            @Query("lat")  double lat,
            @Query("lon") double lon,
            @Query("appid") String appid);


    @GET("forecast")
    Call<ForecastWeathearResponseModel> getForecastWeather(
            @Query("lat")  double lat,
            @Query("lon") double lon,
            @Query("cnt") int cnt,
            @Query("appid") String appid);


}

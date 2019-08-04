package com.github.vaseghifard.weatherapplication.rest;

import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Endpoints {
    @GET("weather")
    Call<CurrentWeatherResponseModel> getCurrentWeather(
            @Query("id") String id,
            @Query("appid") String units);
}

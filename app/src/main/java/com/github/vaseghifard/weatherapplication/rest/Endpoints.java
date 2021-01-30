package com.github.vaseghifard.weatherapplication.rest;

import com.github.vaseghifard.weatherapplication.models.weatherResponse.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Endpoints {

    @GET("onecall")
    Call<WeatherResponse> getWeatherResponse(

            @Query("lat")  double lat,
            @Query("lon") double lon,
            @Query("appid") String appid);


}

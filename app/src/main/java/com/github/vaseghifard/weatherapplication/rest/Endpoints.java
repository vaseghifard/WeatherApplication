package com.github.vaseghifard.weatherapplication.rest;

import com.github.vaseghifard.weatherapplication.models.weatherResponse.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Endpoints {

    @GET("onecall")
    Observable<WeatherResponse> getWeatherResponse(

            @Query("lat")  double lat,
            @Query("lon") double lon,
            @Query("appid") String appid);


}

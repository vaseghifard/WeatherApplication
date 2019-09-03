package com.github.vaseghifard.weatherapplication.currentTemp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.github.vaseghifard.weatherapplication.models.NextDaysItemsModel;
import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;
import com.github.vaseghifard.weatherapplication.models.forecastWaetherResponse.ForecastWeathearResponseModel;
import com.github.vaseghifard.weatherapplication.utils.Constants;
import com.github.vaseghifard.weatherapplication.utils.PublicMethods;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Model implements Contract.Model {
    Contract.Presenter presenter;
    int id;

    String temp,min_temp,max_temp,img_URL,day;
    int imgCode;
    NextDaysItemsModel nextDaysItemsModel;
    ArrayList list = new ArrayList();


    LocationManager locationManager;
    Location totalLocation = null;


    @Override
    public void attachPresenter(Contract.Presenter Presenter) {

        presenter = Presenter;
    }

    @Override
    public void getCurrentLocation(Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        List<String> providers = locationManager.getProviders(true);

        for (String provider : providers) {

            Location location = locationManager.getLastKnownLocation(provider);
            if (location == null) {
                locationManager.requestLocationUpdates(provider, 1000, 0,
                        new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                totalLocation = location;
                            }

                            @Override
                            public void onStatusChanged(String s, int i, Bundle bundle) {

                            }

                            @Override
                            public void onProviderEnabled(String s) {

                            }

                            @Override
                            public void onProviderDisabled(String s) {

                            }
                        });
            } else {
                totalLocation = location;
                Log.e("loc", totalLocation.getLatitude() + "   " + totalLocation.getLongitude() + "");
            }

        }

        presenter.locationSaved(totalLocation);

    }


    @Override
    public void getCurrentTemp(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Constants.endpoints.getCurrentWeather(latitude, longitude, Constants.appId).enqueue(new Callback<CurrentWeatherResponseModel>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponseModel> call, Response<CurrentWeatherResponseModel> response) {
                CurrentWeatherResponseModel responseModel = response.body();
                presenter.currentTempRecieve(responseModel);
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponseModel> call, Throwable t) {
                presenter.onError();
            }
        });
    }

    @Override
    public void getForecastTemp(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();


        Constants.endpoints.getForecastWeather(latitude, longitude, Constants.count, Constants.appId).enqueue(new Callback<ForecastWeathearResponseModel>() {
            @Override
            public void onResponse(Call<ForecastWeathearResponseModel> call, Response<ForecastWeathearResponseModel> response) {
                ForecastWeathearResponseModel model = response.body();

                for (int i = 7; i <= Constants.count; i = i + 8) {
                    min_temp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(model.getList().get(i).getMain().getTempMin()));
                    max_temp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(model.getList().get(i).getMain().getTempMax()));
                    temp = min_temp + "/" + max_temp;
                    day = new Date(model.getList().get(i).getDt() * 1000L).toString().substring(0, 3);
                    //img_URL = Constants.IMAGEURL + model.getList().get(i).getWeather().get(0).getIcon() + "@2x.png";
                    imgCode=model.getList().get(i).getWeather().get(0).getId();
                    Log.e("%%%",day+"*"+ temp+"*"+ imgCode);

                    Log.e("imageCode1",imgCode+"");
                    nextDaysItemsModel = new NextDaysItemsModel(day, temp, imgCode);
                    list.add(nextDaysItemsModel);
                }



                presenter.forecastTempRecieve(list);
            }

            @Override
            public void onFailure(Call<ForecastWeathearResponseModel> call, Throwable t) {
                presenter.onError();

            }
        });

    }

}



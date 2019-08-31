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

import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;
import com.github.vaseghifard.weatherapplication.models.forecastWaetherResponse.ForecastWeathearResponseModel;
import com.github.vaseghifard.weatherapplication.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class Model implements Contract.Model {
    Contract.Presenter presenter;
    int id;

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
                presenter.forecastTempRecieve(model);
            }

            @Override
            public void onFailure(Call<ForecastWeathearResponseModel> call, Throwable t) {
                presenter.onError();

            }
        });

    }

}



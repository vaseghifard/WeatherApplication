package com.github.vaseghifard.weatherapplication.currentTemp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.github.vaseghifard.weatherapplication.models.CurrentWeather;
import com.github.vaseghifard.weatherapplication.models.NextDaysItemsModel;
import com.github.vaseghifard.weatherapplication.models.weatherResponse.WeatherResponse;
import com.github.vaseghifard.weatherapplication.utils.Constants;
import com.github.vaseghifard.weatherapplication.utils.PublicMethods;
import com.orhanobut.hawk.Hawk;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Model implements Contract.Model {
    Contract.Presenter presenter;
    int id;

    String temp, min_temp, max_temp, img_URL, day;
    int imgCode;
    NextDaysItemsModel nextDaysItemsModel;
    ArrayList list = new ArrayList();
    ArrayList list_temp = new ArrayList();


    LocationManager locationManager;
    Location totalLocation = null;


    @Override
    public void attachPresenter(Contract.Presenter Presenter) {

        presenter = Presenter;
    }


    @Override
    public void getCurrentLocation(Context context) {


        boolean connected;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE}, 2);
            return;
        }

        try {
            ConnectivityManager cm = (ConnectivityManager) context.
                    getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            if (connected) {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    return;
                }

                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                assert locationManager != null;
                List<String> providers = locationManager.getProviders(true);

                for (String provider : providers) {
                    Location location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {
                        totalLocation = location;

                    } else {
                        locationManager.requestLocationUpdates(provider, 1000, 0,
                                new LocationListener() {
                                    @Override
                                    public void onLocationChanged(Location location) {
                                        locationManager.removeUpdates(this);
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

                    }

                }



                presenter.locationSaved(totalLocation);

            } else {

                presenter.currentTempRecieve((CurrentWeather) Hawk.get("CurrentWeather"));
                presenter.forecastTempRecieve((ArrayList) Hawk.get("foreCastTemp"));
            }

        } catch (Exception e) {
            Log.e("Connectivity Exception", "" + e.getMessage());
        }


    }

    @Override
    public void getCurrentTemp(Location location) {
        list_temp.clear();

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();


       Constants.endpoints.getWeatherResponse(latitude, longitude, Constants.appId)
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                        WeatherResponse weatherResponse = response.body();
                        for (int i = 0; i < 24; i++) {

                            list_temp.add(weatherResponse.getHourly().get(i).getTemp());
                        }

                        CurrentWeather currentWeather = new CurrentWeather(
                                weatherResponse.getTimezone().substring(weatherResponse.getTimezone().lastIndexOf("/") + 1),
                                weatherResponse.getCurrent().getWeather().get(0).getDescription(),
                                weatherResponse.getCurrent().getWeather().get(0).getId(),
                                Collections.min(list_temp),
                                Collections.max(list_temp),
                                weatherResponse.getCurrent().getTemp(),
                                weatherResponse.getCurrent().getHumidity(),
                                weatherResponse.getCurrent().getWindSpeed(),
                                new Date(weatherResponse.getCurrent().getDt() * 1000L));


                        Hawk.put("CurrentWeather", currentWeather);
                        presenter.currentTempRecieve(currentWeather);
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        presenter.onError();
                    }
                });
    }

    @Override
    public void getForecastTemp(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();


        Constants.endpoints.getWeatherResponse(latitude, longitude, Constants.appId)
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        WeatherResponse weatherResponse = response.body();
                        list.clear();

                        for (int i = 1; i < 5; i++) {
                            day = new Date(weatherResponse.getDaily().get(i).getDt() * 1000L).toString().substring(0, 3);
                            min_temp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(weatherResponse.getDaily().get(i).getTemp().getMin()));
                            max_temp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(weatherResponse.getDaily().get(i).getTemp().getMax()));
                            temp = min_temp + "/" + max_temp;
                            imgCode = weatherResponse.getDaily().get(i).getWeather().get(0).getId();
                            nextDaysItemsModel = new NextDaysItemsModel(day, temp, imgCode);
                            list.add(nextDaysItemsModel);
                        }


                        Hawk.put("foreCastTemp", list);
                        presenter.forecastTempRecieve(list);
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        presenter.onError();
                    }
                });
    }

}







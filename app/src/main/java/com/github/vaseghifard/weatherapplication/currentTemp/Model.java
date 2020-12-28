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
import com.github.vaseghifard.weatherapplication.models.forecastWaetherResponse.ForecastWeathearResponseModel;
import com.github.vaseghifard.weatherapplication.utils.Constants;
import com.github.vaseghifard.weatherapplication.utils.PublicMethods;
import com.orhanobut.hawk.Hawk;

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
    ArrayList list_min_temp = new ArrayList();
    ArrayList list_max_temp = new ArrayList();
    ArrayList list_days_min = new ArrayList();
    ArrayList list_days_max = new ArrayList();

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
            Log.e("Connectivity Exception", e.getMessage());
        }


    }


    @Override
    public void getCurrentTemp(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Constants.endpoints.getForecastWeather(latitude, longitude, Constants.count_current, Constants.appId)
                .enqueue(new Callback<ForecastWeathearResponseModel>() {
                    @Override
                    public void onResponse(Call<ForecastWeathearResponseModel> call, Response<ForecastWeathearResponseModel> response) {
                        ForecastWeathearResponseModel responseModel = response.body();


                        responseModel.getList().get(1).getWeather().get(0).getId();
                        for (int i = 0; i < Constants.count_current; i++) {
                            list_min_temp.add(responseModel.getList().get(i).getMain().getTempMin());
                            list_max_temp.add(responseModel.getList().get(i).getMain().getTempMax());
                        }

                        CurrentWeather currentWeather = new CurrentWeather(responseModel.getCity().getName(),
                                responseModel.getList().get(1).getWeather().get(0).getMain()
                                , responseModel.getList().get(1).getWeather().get(0).getId(),
                                Collections.min(list_min_temp)
                                , Collections.max(list_max_temp),
                                responseModel.getList().get(1).getMain().getTemp());


                        Hawk.put("CurrentWeather", currentWeather);
                        presenter.currentTempRecieve(currentWeather);
                    }

                    @Override
                    public void onFailure(Call<ForecastWeathearResponseModel> call, Throwable t) {

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
                list.clear();
                for (int j = 6; j <= Constants.count; j = j + 5){
                for(int i=j-6;i<j;i++)
                {
                    list_min_temp.add(model.getList().get(i).getMain().getTempMin());
                    list_max_temp.add(model.getList().get(i).getMain().getTempMax());


                }
                    list_days_min.add(Collections.min(list_min_temp));
                    list_days_max.add(Collections.max(list_max_temp));
                    for (int k=0;k<list_days_min.size();k++){
                        min_temp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC((Double) list_days_min.get(k)));
                        max_temp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC((Double) list_days_max.get(k)));
                        temp = min_temp + "/" + max_temp;
                    }

                    day = new Date(model.getList().get(j).getDt() * 1000L).toString().substring(0, 3);
                    imgCode = model.getList().get(j).getWeather().get(0).getId();
                    nextDaysItemsModel = new NextDaysItemsModel(day, temp, imgCode);
                    list.add(nextDaysItemsModel);
                }

               /* for (int i = 7; i <= Constants.count; i = i + 8) {
                    min_temp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(model.getList().get(i).getMain().getTempMin()));
                    max_temp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(model.getList().get(i).getMain().getTempMax()));
                    temp = min_temp + "/" + max_temp;
                    day = new Date(model.getList().get(i).getDt() * 1000L).toString().substring(0, 3);
                    imgCode = model.getList().get(i).getWeather().get(0).getId();
                    nextDaysItemsModel = new NextDaysItemsModel(day, temp, imgCode);
                    list.add(nextDaysItemsModel);
                }*/

                Hawk.put("foreCastTemp", list);
                presenter.forecastTempRecieve(list);
            }

            @Override
            public void onFailure(Call<ForecastWeathearResponseModel> call, Throwable t) {
                presenter.onError();

            }
        });

    }

}



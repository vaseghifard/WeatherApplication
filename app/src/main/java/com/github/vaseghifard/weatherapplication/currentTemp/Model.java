package com.github.vaseghifard.weatherapplication.currentTemp;

import android.location.Location;
import android.provider.Contacts;

import com.github.vaseghifard.weatherapplication.models.cities.CitiesModel;
import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;
import com.github.vaseghifard.weatherapplication.utils.BaseActivity;
import com.github.vaseghifard.weatherapplication.utils.Constants;
import com.github.vaseghifard.weatherapplication.utils.MyApplication;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


public class Model implements Contract.Model {
    Contract.Presenter presenter;
    String id;


    @Override
    public void attachPresenter(Contract.Presenter Presenter) {

        presenter = Presenter;
    }

    @Override
    public void getCurrentLocation(Location location) {

        String json = loadJSONFromAsset();
        List<CitiesModel> citiesModel = Arrays.asList(new Gson().fromJson(json, CitiesModel.class));

        for (CitiesModel city : citiesModel) {
            if (city.getCoord().getLat().equals(location.getLatitude()) &&
                    city.getCoord().getLon().equals(location.getLongitude())) {

                id = city.getId();
            }

        }


    }


    @Override
    public void getCurrentTemp() {

        Constants.endpoints.getCurrentWeather(id, Constants.appId).enqueue(new Callback<CurrentWeatherResponseModel>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponseModel> call, Response<CurrentWeatherResponseModel> response) {
                CurrentWeatherResponseModel responseModel = response.body();
                presenter.currentTempRecieve(responseModel);
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponseModel> call, Throwable t) {

            }
        });
    }


    public String loadJSONFromAsset() {
        String json = null;

        try {
            InputStream is = MyApplication.appInstance.getAssets().open("city.list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

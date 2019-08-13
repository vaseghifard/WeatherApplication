package com.github.vaseghifard.weatherapplication.currentTemp;

import android.content.Context;
import android.location.Location;
import android.view.View;

import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;

public class Presenter implements Contract.Presenter {
    Model model = new Model();
    Contract.View view;

    @Override
    public void attachView(Contract.View view) {
        this.view = view;
        model.attachPresenter(this);
    }

    @Override
    public void getCurrentLocation(Context context) {
        model.getCurrentLocation(context);

    }

    @Override
    public void locationSaved(Location location) {
        view.locationSaved(location);
    }


    @Override
    public void getCurrentTemp(Location location) {
        model.getCurrentTemp(location);
    }

    @Override
    public void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel) {
        view.currentTempRecieve(weatherResponseModel);
    }


    @Override
    public void onError() {
        view.onError();
    }
}

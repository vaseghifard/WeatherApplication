package com.github.vaseghifard.weatherapplication.currentTemp;

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
    public void getCurrentLocation(Location location) {
        model.getCurrentLocation(location );

    }

    @Override
    public void getCurrentTemp() {
        model.getCurrentTemp();
    }

    @Override
    public void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel) {
        view.currentTempRecieve(weatherResponseModel);
    }


    @Override
    public void onError() {

    }
}

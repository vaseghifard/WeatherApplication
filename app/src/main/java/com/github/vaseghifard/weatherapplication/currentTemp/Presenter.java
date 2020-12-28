package com.github.vaseghifard.weatherapplication.currentTemp;

import android.content.Context;
import android.location.Location;
import com.github.vaseghifard.weatherapplication.models.CurrentWeather;
import java.util.ArrayList;


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
    public void getForecastTemp(Location location) {
        model.getForecastTemp(location);

    }

    @Override
    public void forecastTempRecieve(ArrayList list) {
        view.forecastTempRecieve(list);
    }


    @Override
    public void currentTempRecieve(CurrentWeather currentWeather) {
        view.currentTempRecieve(currentWeather);
    }

    @Override
    public void onError() {
        view.onError();
    }
}

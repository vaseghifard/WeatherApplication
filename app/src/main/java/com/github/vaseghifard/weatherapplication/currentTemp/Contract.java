package com.github.vaseghifard.weatherapplication.currentTemp;

import android.content.Context;
import android.location.Location;

import com.github.vaseghifard.weatherapplication.models.CurrentWeather;
import java.util.ArrayList;


public interface Contract {

    interface View{
        void forecastTempRecieve(ArrayList list);
        void currentTempRecieve(CurrentWeather currentWeather);
        void locationSaved(Location location);
        void onError();

    }
    interface Presenter{
        void attachView(View view);
        void getCurrentLocation(Context context);
        void locationSaved(Location location);
        void getCurrentTemp(Location location);
        void getForecastTemp(Location location);
        void forecastTempRecieve(ArrayList list);
        void currentTempRecieve(CurrentWeather currentWeather);
        void onError();

    }
    interface Model{
        void attachPresenter(Presenter Presenter);
        void getCurrentLocation(Context context);
        void getCurrentTemp(Location location);
        void getForecastTemp(Location location);
    }
}

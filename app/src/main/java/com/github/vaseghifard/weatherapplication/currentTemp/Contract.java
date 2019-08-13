package com.github.vaseghifard.weatherapplication.currentTemp;

import android.content.Context;
import android.location.Location;

import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;

public interface Contract {

    interface View{
        void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel);
        void locationSaved(Location location);
        void onError();

    }
    interface Presenter{
        void attachView(View view);
        void getCurrentLocation(Context context);
        void locationSaved(Location location);
        void getCurrentTemp(Location location);
        void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel);
        void onError();

    }
    interface Model{
        void attachPresenter(Presenter Presenter);
        void getCurrentLocation(Context context);
        void getCurrentTemp(Location location);
    }
}

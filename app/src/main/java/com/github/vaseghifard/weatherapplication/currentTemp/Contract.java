package com.github.vaseghifard.weatherapplication.currentTemp;

import android.location.Location;

import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;

public interface Contract {

    interface View{
        void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel);
        void onError();

    }
    interface Presenter{
        void attachView(View view);
        void getCurrentLocation(Location location);
        void getCurrentTemp();
        void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel);
        void onError();

    }
    interface Model{
        void attachPresenter(Presenter Presenter);
        void getCurrentLocation(Location location);
        void getCurrentTemp();
    }
}

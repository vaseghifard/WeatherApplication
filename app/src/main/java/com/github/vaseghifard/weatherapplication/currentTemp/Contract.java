package com.github.vaseghifard.weatherapplication.currentTemp;

import android.content.Context;
import android.location.Location;

import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;
import com.github.vaseghifard.weatherapplication.models.forecastWaetherResponse.ForecastWeathearResponseModel;

public interface Contract {

    interface View{
        void forecastTempRecieve(ForecastWeathearResponseModel forecastWeathearResponseModel);
        void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel);
        void locationSaved(Location location);
        void onError();

    }
    interface Presenter{
        void attachView(View view);
        void getCurrentLocation(Context context);
        void locationSaved(Location location);
        void getCurrentTemp(Location location);
        void getForecastTemp(Location location);
        void forecastTempRecieve(ForecastWeathearResponseModel forecastWeathearResponseModel);
        void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel);
        void onError();

    }
    interface Model{
        void attachPresenter(Presenter Presenter);
        void getCurrentLocation(Context context);
        void getCurrentTemp(Location location);
        void getForecastTemp(Location location);
    }
}

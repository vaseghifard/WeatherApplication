package com.github.vaseghifard.weatherapplication.currentTemp;

import android.content.Context;
import android.location.Location;

import com.github.vaseghifard.weatherapplication.models.CurrentWeather;
import java.util.ArrayList;


public interface Contract {

    interface View{

        void currentTempRecieve(CurrentWeather currentWeather,ArrayList list);
        void locationSaved(Location location);
        void onError();
        void onSearch(Void aVoid);

    }
    interface Presenter{
        void attachView(View view);
        void getCurrentLocation(Context context);
        void locationSaved(Location location);
        void getCurrentTemp(Location location);
        void currentTempRecieve(CurrentWeather currentWeather,ArrayList list);
        void onError();

    }
    interface Model{
        void attachPresenter(Presenter Presenter);
        void getCurrentLocation(Context context);
        void getCurrentTemp(Location location);
    }
}

package com.github.vaseghifard.weatherapplication.currentTemp;

import android.location.Location;

public interface Contract {

    interface View{
        void currentTempRecieve();
        void onError();

    }
    interface Presenter{
        void attachView(View view);
        void getCurrentLocation(Location location);
        void getCurrentTemp();
        void currentTempRecieve();
        void onError();

    }
    interface Model{
        void attachPresenter(Presenter Presenter);
        void getCurrentLocation(Location location);
        void getCurrentTemp();
    }
}

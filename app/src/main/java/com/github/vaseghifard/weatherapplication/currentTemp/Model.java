package com.github.vaseghifard.weatherapplication.currentTemp;

import android.location.Location;

import com.github.vaseghifard.weatherapplication.utils.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

public class Model implements Contract.Model {
    Contract.Presenter presenter;


    @Override
    public void attachPresenter(Contract.Presenter Presenter) {

        presenter = Presenter;
    }

    @Override
    public void getCurrentLocation(Location location) {

    }



    @Override
    public void getCurrentTemp() {

        //Constants.endpoints.getCurrentWeather()
    }


    public String loadJSONFromAsset() {
        String json = null;

        try {
            BaseActivity.
            InputStream is = getAssets().open("yourfilename.json");
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

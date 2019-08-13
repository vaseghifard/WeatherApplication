package com.github.vaseghifard.weatherapplication.currentTemp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.vaseghifard.weatherapplication.R;
import com.github.vaseghifard.weatherapplication.adapters.NextDaysItemsAdapter;
import com.github.vaseghifard.weatherapplication.currentTemp.Contract;
import com.github.vaseghifard.weatherapplication.currentTemp.Presenter;
import com.github.vaseghifard.weatherapplication.customViews.MyTextView;
import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;
import com.github.vaseghifard.weatherapplication.utils.BaseActivity;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity implements Contract.View {

    RecyclerView recyclerView;
    MyTextView city_name,current_temperature,time;
    Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        presenter.attachView(this);
        recyclerView = findViewById(R.id.items_future);
        city_name = findViewById(R.id.city_name);
        current_temperature = findViewById(R.id.current_temperature);
        time = findViewById(R.id.time);


        presenter.getCurrentLocation(mContext);



   /*     NextDaysItemsAdapter adapter = new NextDaysItemsAdapter(
                mContext , books);
        recyclerView.setAdapter(adapter);*/
    }


    @Override
    public void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel) {
        city_name.setText(weatherResponseModel.getName());
        time.setText(new Date(weatherResponseModel.getDt() * 1000L).toString());
        current_temperature.setText(weatherResponseModel.getMain().getTemp().toString());


    }

    @Override
    public void locationSaved(Location location) {
        presenter.getCurrentTemp(location);
    }

    @Override
    public void onError() {

    }

}

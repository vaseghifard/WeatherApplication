package com.github.vaseghifard.weatherapplication.currentTemp;

import android.animation.ValueAnimator;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.github.vaseghifard.weatherapplication.R;
import com.github.vaseghifard.weatherapplication.adapters.NextDaysItemsAdapter;
import com.github.vaseghifard.weatherapplication.customViews.MyImageView;
import com.github.vaseghifard.weatherapplication.customViews.MyTextView;
import com.github.vaseghifard.weatherapplication.models.CurrentWeather;
import com.github.vaseghifard.weatherapplication.utils.BaseActivity;
import com.github.vaseghifard.weatherapplication.utils.PublicMethods;
import com.jakewharton.rxbinding.view.RxView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MainActivity extends BaseActivity implements Contract.View {

    RecyclerView recyclerView;
    MyTextView city_name, current_temperature, min, max, weather_description, wind_speed, humidity, time;
    MyImageView current_temperature_image, search, reload;
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
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);
        wind_speed = findViewById(R.id.wind_speed);
        humidity = findViewById(R.id.humidity);
        time = findViewById(R.id.time);
        search = findViewById(R.id.search);
        reload = findViewById(R.id.reload);
        current_temperature_image = findViewById(R.id.current_temperature_image);
        weather_description = findViewById(R.id.weather_description);

        presenter.getCurrentLocation(mContext);
        RxView.clicks(reload)
                .debounce(1000, TimeUnit.MICROSECONDS)
                .subscribe(this::onReload);

    }


    @Override
    public void currentTempRecieve(CurrentWeather currentWeather, ArrayList list) {
        Log.e("temprecived", "temprecived");

        city_name.setText(currentWeather.getCity_name());
        weather_description.setText(currentWeather.getWeather_description());
        current_temperature_image.load(this, currentWeather.getCurrent_temperature_image());
        String minTemp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC((Double) currentWeather.getMinTemp()));
        String maxTemp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC((Double) currentWeather.getMaxTemp()));
        min.setText(minTemp);
        max.setText(maxTemp);
        current_temperature.setText(String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(currentWeather.getCurrent_temperature())) + "C");
        humidity.setText(currentWeather.getHumidity() + "%");
        wind_speed.setText(String.format("%.0f", currentWeather.getSpeed_wind()) + "km/h");
        time.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(currentWeather.getDate()));


        NextDaysItemsAdapter adapter = new NextDaysItemsAdapter(
                mContext, list);
        recyclerView.setAdapter(adapter);
        reload.setAnimation(null);

    }

    @Override
    public void locationSaved(Location location) {

        presenter.getCurrentTemp(location);

    }

    @Override
    public void onError() {

    }

    @Override
    public void onReload(Void aVoid) {
        this.runOnUiThread(() -> {
            Log.e("reload", "reload");
            RotateAnimation animation = new RotateAnimation(0f, 360f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(800);
            animation.setRepeatMode(ValueAnimator.RESTART);
            reload.startAnimation(animation);
            presenter.getCurrentLocation(mContext);

        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCurrentLocation(mContext);
    }
}
package com.github.vaseghifard.weatherapplication.currentTemp;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.github.vaseghifard.weatherapplication.R;
import com.github.vaseghifard.weatherapplication.adapters.NextDaysItemsAdapter;
import com.github.vaseghifard.weatherapplication.customViews.MyImageView;
import com.github.vaseghifard.weatherapplication.customViews.MyTextView;
import com.github.vaseghifard.weatherapplication.models.NextDaysItemsModel;
import com.github.vaseghifard.weatherapplication.models.currentWeatherResponse.CurrentWeatherResponseModel;
import com.github.vaseghifard.weatherapplication.models.forecastWaetherResponse.ForecastWeathearResponseModel;
import com.github.vaseghifard.weatherapplication.models.forecastWaetherResponse.List;
import com.github.vaseghifard.weatherapplication.utils.BaseActivity;
import com.github.vaseghifard.weatherapplication.utils.Constants;
import com.github.vaseghifard.weatherapplication.utils.PublicMethods;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends BaseActivity implements Contract.View {

    RecyclerView recyclerView;
    MyTextView city_name, current_temperature, min_max, weather_description;
    MyImageView current_temperature_image;
    CardView cardView;
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
        min_max = findViewById(R.id.min_max);
        current_temperature_image = findViewById(R.id.current_temperature_image);
        weather_description = findViewById(R.id.weather_description);
        cardView = findViewById(R.id.cardView);

        cardView.setCardBackgroundColor(getResources().getColor(R.color.backgroundCardView));

        presenter.getCurrentLocation(mContext);


    }


    @Override
    public void forecastTempRecieve(ArrayList list) {

        NextDaysItemsAdapter adapter = new NextDaysItemsAdapter(
                mContext, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void currentTempRecieve(CurrentWeatherResponseModel weatherResponseModel) {
        city_name.setText(weatherResponseModel.getName());
        weather_description.setText(weatherResponseModel.getWeather().get(0).getMain());
        current_temperature_image.load(this,  weatherResponseModel.getWeather().get(0).getId());

        String minTemp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(weatherResponseModel.getMain().getTempMin()));
        String maxTemp = String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(weatherResponseModel.getMain().getTempMax()));
        min_max.setText(minTemp + "/" + maxTemp);


        current_temperature.setText(String.format(Locale.getDefault(), "%.0f°", PublicMethods.convertKToC(weatherResponseModel.getMain().getTemp())));


    }

    @Override
    public void locationSaved(Location location) {
        presenter.getCurrentTemp(location);
        presenter.getForecastTemp(location);
    }

    @Override
    public void onError() {

    }

}

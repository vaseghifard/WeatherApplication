package com.github.vaseghifard.weatherapplication.currentTemp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.github.vaseghifard.weatherapplication.R;
import com.github.vaseghifard.weatherapplication.adapters.NextDaysItemsAdapter;
import com.github.vaseghifard.weatherapplication.currentTemp.Contract;
import com.github.vaseghifard.weatherapplication.currentTemp.Presenter;
import com.github.vaseghifard.weatherapplication.utils.BaseActivity;

public class MainActivity extends BaseActivity implements Contract.View, LocationListener {

    RecyclerView recyclerView;
    Presenter presenter = new Presenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.attachView(this);
        recyclerView = findViewById(R.id.items_future);



        //get current location when user open app
        LocationManager locationManager = (LocationManager) getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }


        presenter.getCurrentTemp();



   /*     NextDaysItemsAdapter adapter = new NextDaysItemsAdapter(
                mContext , books);
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    public void currentTempRecieve() {

    }

    @Override
    public void onError() {

    }

    //get location for set weather
    @Override
    public void onLocationChanged(Location location) {
        presenter.getCurrentLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

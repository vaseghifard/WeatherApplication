package com.github.vaseghifard.weatherapplication.customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.github.vaseghifard.weatherapplication.R;

public class MyImageView extends AppCompatImageView {
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void load(Context mContext, String url) {

        Glide.with(mContext).load(url).into(this);
    }

    public void load(Context mContext, Bitmap bitmap) {
        Glide.with(mContext).load(bitmap).into(this);
    }


    public void load(Context mContext, int imageCode) {
        if (imageCode / 100 == 2) {
            Glide.with(mContext).load(R.drawable.ic_storm_weather).into(this);
        } else if (imageCode / 100 == 3) {
            Glide.with(mContext).load(R.drawable.ic_rainy_weather).into(this);
        } else if (imageCode / 100 == 5) {
            Glide.with(mContext).load(R.drawable.ic_rainy_weather).into(this);
        } else if (imageCode / 100 == 6) {
            Glide.with(mContext).load(R.drawable.ic_snow_weather).into(this);
        } else if (imageCode / 100 == 7) {
            Glide.with(mContext).load(R.drawable.ic_mist).into(this);
        } else if (imageCode == 800) {
            Glide.with(mContext).load(R.drawable.ic_clear_day).into(this);
        } else if (imageCode == 801) {
            Glide.with(mContext).load(R.drawable.ic_few_clouds).into(this);
        } else if (imageCode == 803) {
            Glide.with(mContext).load(R.drawable.ic_broken_clouds).into(this);
        } else if (imageCode / 100 == 8) {
            Glide.with(mContext).load(R.drawable.ic_cloudy_weather).into(this);
        }
    }
}

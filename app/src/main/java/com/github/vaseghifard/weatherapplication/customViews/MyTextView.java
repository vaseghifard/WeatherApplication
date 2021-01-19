package com.github.vaseghifard.weatherapplication.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.github.vaseghifard.weatherapplication.R;

public class MyTextView extends AppCompatTextView {


    public MyTextView(Context context) {
        super(context);
        this.setTypeface(this.getTypeface(), Typeface.ITALIC);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(this.getTypeface(), Typeface.ITALIC);
    }
}

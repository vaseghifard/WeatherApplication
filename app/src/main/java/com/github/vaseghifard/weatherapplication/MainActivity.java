package com.github.vaseghifard.weatherapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.github.vaseghifard.weatherapplication.adapters.NextDaysItemsAdapter;
import com.github.vaseghifard.weatherapplication.utils.BaseActivity;

public class MainActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.items_future);


   /*     NextDaysItemsAdapter adapter = new NextDaysItemsAdapter(
                mContext , books);
        recyclerView.setAdapter(adapter);*/
    }
}

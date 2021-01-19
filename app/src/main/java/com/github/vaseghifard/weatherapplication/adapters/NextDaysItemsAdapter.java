package com.github.vaseghifard.weatherapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vaseghifard.weatherapplication.R;
import com.github.vaseghifard.weatherapplication.customViews.MyImageView;
import com.github.vaseghifard.weatherapplication.customViews.MyTextView;
import com.github.vaseghifard.weatherapplication.models.NextDaysItemsModel;

import java.util.List;

public class NextDaysItemsAdapter extends RecyclerView.Adapter<NextDaysItemsAdapter.Holder> {

    Context mContext;
    List<NextDaysItemsModel> nextDaysItems;

    public NextDaysItemsAdapter(Context mContext, List<NextDaysItemsModel> nextDaysItems) {
        this.mContext = mContext;
        this.nextDaysItems = nextDaysItems;
    }


    @Override
    public NextDaysItemsAdapter.Holder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.next_days_item, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NextDaysItemsAdapter.Holder holder, int i) {
        holder.name.setText(nextDaysItems.get(i).getNameOfDays());
        holder.temp.setText(nextDaysItems.get(i).getTempOfdays());
        holder.image.load(mContext, nextDaysItems.get(i).getCodeImageOfDays());
    }

    @Override
    public int getItemCount() {
        return nextDaysItems.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public MyTextView name, temp;
        public MyImageView image;

        public Holder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameOfDay);
            temp = itemView.findViewById(R.id.tempOfDay);
            image = itemView.findViewById(R.id.imageOfDay);
        }
    }
}

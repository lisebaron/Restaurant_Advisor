package com.example.resto.Restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.resto.R;
import com.example.resto.Restaurant.Restaurant;

import java.util.List;

public class listViewRestoAdapter extends BaseAdapter {
    private Context context;
    private List<Restaurant> restaurantList;


    public listViewRestoAdapter(Context context, List restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.restaurant, null);

        Restaurant restaurant = restaurantList.get(position);

        TextView textViewRestaurantName = (TextView) convertView.findViewById(R.id.restaurant_name);

        textViewRestaurantName.setText(restaurant.getName());

        return convertView;
    }
}

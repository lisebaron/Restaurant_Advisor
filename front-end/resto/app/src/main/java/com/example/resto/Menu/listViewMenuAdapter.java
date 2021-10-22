package com.example.resto.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.resto.Menu.Menu;
import com.example.resto.R;

import java.util.List;

public class listViewMenuAdapter extends BaseAdapter {

    private Context context;
    private List<Menu> menuList;

    public listViewMenuAdapter(Context context, List menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.menu, null);

        Menu menu = menuList.get(position);

        TextView textViewMenuName = (TextView) convertView.findViewById(R.id.menu_name);

        textViewMenuName.setText(menu.getName());

        return convertView;
    }
}

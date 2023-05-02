package com.example.rkuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuBarAdapter extends BaseAdapter {
    Context context;
    String[] flowerName;
    int[] image;

    LayoutInflater inflater;

    public MenuBarAdapter(Context context, String[] flowerName, int[] image) {
        this.context = context;
        this.flowerName = flowerName;
        this.image = image;
    }

    @Override
    public int getCount() {
        return flowerName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_menu_items, null);
        }

        ImageView imageView = convertView.findViewById(R.id.gridMenuImage);
        TextView textView = convertView.findViewById(R.id.gridMenuTitle);

        imageView.setImageResource(image[position]);
        textView.setText(flowerName[position]);

        return convertView;
    }
}

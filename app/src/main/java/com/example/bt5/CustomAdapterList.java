package com.example.bt5;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapterList extends ArrayAdapter {
    private Activity context;
    private String[] name;
    private Integer[] image_id;
    private int activated ;
    public CustomAdapterList(Activity context, String[] name, Integer[] image_id, int activated){
        super(context, R.layout.list_item, name);
        this.context = context;
        this.name = name;
        this.image_id = image_id;
        this.activated = activated;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row =convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.list_item, null, true);
        TextView nameTxt = (TextView) row.findViewById(R.id.nameView);
        ImageView image = (ImageView) row.findViewById(R.id.imageView);
        nameTxt.setText(name[position]);
        image.setImageResource(image_id[position]);
        if (position == activated){
            row.setBackgroundColor(R.color.light_red);
        }
        else {
            row.setBackground(null);
        }
        return row;
    }
}

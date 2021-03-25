package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomAdapterList extends ArrayAdapter {
    private String[] name_items;
    private String[] phone_items;
    private Integer[] imageid;
    private Activity context;

    public CustomAdapterList(Activity context, String[] name_items, String[] phone_items, Integer[] imageid) {
        super(context, R.layout.row_item, name_items);
        this.context = context;
        this.name_items = name_items;
        this.phone_items = phone_items;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row =convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item, null, true);
        TextView textViewName = (TextView) row.findViewById(R.id.textViewName);
        TextView textViewPhone = (TextView) row.findViewById(R.id.phoneView);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.avatarView);

        textViewName.setText(name_items[position]);
        textViewPhone.setText(phone_items[position]);
        return row;
    }
}
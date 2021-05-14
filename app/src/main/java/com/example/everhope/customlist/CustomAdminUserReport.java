package com.example.everhope.customlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.everhope.R;

public class CustomAdminUserReport extends ArrayAdapter {

    Activity context;
    String [] reportID;


    public CustomAdminUserReport(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null){
            root = inflater.inflate(R.layout.admin_rp_item, null, true);

        }

        return root;
    }
}

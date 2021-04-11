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

public class CustomUserList extends ArrayAdapter {
    Activity context;
    String[] userID;
    String[] name;
    int[] total_event;

    public CustomUserList(@NonNull Activity context, String[] userID, String[] name, int[] total_event) {
        super(context, R.layout.lb_custom);
        this.userID = userID;
        this.name = name;
        this.total_event = total_event;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null){
            root = inflater.inflate(R.layout.lb_custom, null, true);
        }

        return root;
    }
}

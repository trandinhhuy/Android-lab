package com.example.everhope.customlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.everhope.EventInformation;
import com.example.everhope.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CustomEventList extends ArrayAdapter {
    private Activity context;
    private String[] name;
    private String[] eventID;
    private String[] date;
    Integer layout;

    public CustomEventList(@NonNull Activity context, String[] eventID, String[] name, String[] date, Integer layout) {
        super(context, R.layout.event_view_full, name);
        this.name = name;
        this.context = context;
        this.eventID = eventID;
        this.date = date;
        this.layout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = convertView;
        LayoutInflater inflater =   context.getLayoutInflater();
        if (convertView == null){
            root = inflater.inflate(layout, null, true);
        }
        TextView Name = (TextView) root.findViewById(R.id.caption);
        TextView Date = (TextView) root.findViewById(R.id.date);
        Name.setText(name[position]);
        Date.setText(date[position]);
        EventInformation.setImage("Event/Event" + eventID[position], R.id.iconImg, root);
        return root;
    }
}

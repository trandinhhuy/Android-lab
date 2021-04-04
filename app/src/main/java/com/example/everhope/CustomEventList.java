package com.example.everhope;

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

public class CustomEventList extends ArrayAdapter {
    private Activity context;
    private String[] topic;
    private int[] members;
    private Integer[] images;

    public CustomEventList(@NonNull Activity context, Integer[] images, String[] topic, int[] members) {
        super(context, R.layout.activity_view, topic);
        this.context = context;
        this.images = images;
        this.topic = topic;
        this.members = members;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = convertView;
        LayoutInflater inflater =   context.getLayoutInflater();
        if (convertView == null){
            root = inflater.inflate(R.layout.activity_view, null, true);
        }
        ImageView icon_image = (ImageView) root.findViewById(R.id.iconImg);
        TextView Topic = (TextView) root.findViewById(R.id.caption);
        TextView member = (TextView) root.findViewById(R.id.date);
        icon_image.setImageResource(images[position]);
        Topic.setText(topic[position]);
        member.setText(String.valueOf(this.members[position]) + " members");
        return root;
    }
}

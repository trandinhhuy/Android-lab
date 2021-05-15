package com.example.everhope;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private String eventID;
    private int totalImage;

    public ImageAdapter(Context context, String eventID, int totalImage){
        this.context = context;
        this.eventID = eventID;
        this.totalImage = totalImage;
    }

    @Override
    public int getCount() {
        return totalImage;
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
        return null;
    }
}

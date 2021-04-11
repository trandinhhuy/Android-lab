package com.example.everhope.customlist;

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

import com.example.everhope.R;

public class CustomCommentList extends ArrayAdapter {
    Activity context;
    Integer[] avatar;
    String[] name;
    String[] comment;

    public CustomCommentList(@NonNull Activity context, Integer[] avatar, String[] name, String[] comment) {
        super(context, R.layout.single_comment, name);
        this.avatar = avatar;
        this.name = name;
        this.comment = comment;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null){
            root = inflater.inflate(R.layout.single_comment, null, true);
        }
        ImageView user_image = root.findViewById(R.id.user_comment_image);
        TextView user_comment = root.findViewById(R.id.user_comment);
        TextView user_name = root.findViewById(R.id.user_name);

        user_image.setImageResource(avatar[position]);
        user_name.setText(name[position]);
        user_comment.setText(comment[position]);
        return root;
    }
}

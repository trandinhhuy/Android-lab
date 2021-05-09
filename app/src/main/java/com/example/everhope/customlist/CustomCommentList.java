package com.example.everhope.customlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.everhope.userProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomCommentList extends ArrayAdapter {
    Activity context;
    String[] UID;
    String[] comment;
    String[] date;
    String[] time;

    public CustomCommentList(@NonNull Activity context, String[] UID, String[] comment, String[] date, String[] time) {
        super(context, R.layout.single_comment, UID);
        this.UID = UID;
        this.comment = comment;
        this.date = date;
        this.time = time;
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
        TextView user_comment = root.findViewById(R.id.user_comment);
        TextView user_name = root.findViewById(R.id.user_name);
        ImageView userCommentImage = (ImageView) root.findViewById(R.id.user_comment_image);

        // bên list đẩy id người dùng qua thì lấy name với avatar nè
        EventInformation.setImage("Avatar/User" + UID[position], R.id.user_comment_image, root);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User/" + UID[position]);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_name.setText(String.valueOf(snapshot.child("Name").getValue())); // set name luôn
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        user_comment.setText(comment[position]);
        userCommentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), userProfile.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserID", UID[position]);
                intent.putExtras(bundle);
                context.startActivity(intent); // t làm thêm bấm vô avatar thì coi thông tin người dùng
            }
        });
        return root;
    }
}

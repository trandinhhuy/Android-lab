package com.example.everhope.customlist;

import android.annotation.SuppressLint;
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
        TextView cmt_dt = root.findViewById(R.id.cmt_dt);
        ImageView userCommentImage = (ImageView) root.findViewById(R.id.user_comment_image);
        EventInformation.setImage("Avatar/User" + UID[position], R.id.user_comment_image, root);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User/" + UID[position]);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String banned = String.valueOf(snapshot.child("Ban").getValue());
                if(banned.compareTo("1")!=0){
                    user_name.setText(String.valueOf(snapshot.child("Name").getValue()));
                    user_comment.setText(comment[position]);
                    String cmt = time[position]+" "+date[position];
                    cmt_dt.setText(cmt);
                    userCommentImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), userProfile.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("UserID", UID[position]);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
                else{
                    user_comment.setVisibility(View.GONE);
                    user_name.setVisibility(View.GONE);
                    userCommentImage.setVisibility(View.GONE);
                    cmt_dt.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}

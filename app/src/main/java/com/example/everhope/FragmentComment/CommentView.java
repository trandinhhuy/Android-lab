package com.example.everhope.FragmentComment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.everhope.EventInformation;
import com.example.everhope.R;
import com.example.everhope.customlist.CustomCommentList;
import com.example.everhope.customlist.CustomEventList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class CommentView extends Fragment {
    Context context = null;

    List<String> cmt = new ArrayList<>();
    List<String> UID = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    List<String> timeList = new ArrayList<>();

    String[] userID;
    String[] comment;
    String[] date;
    String[] time;

    public static CommentView newInstance() {

        Bundle args = new Bundle();

        CommentView fragment = new CommentView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
        } catch(IllegalStateException e){

        }
        //db
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        LinearLayout commentView = (LinearLayout) inflater.inflate(R.layout.comment_view, null, true);
        ListView commentList = (ListView) commentView.findViewById(R.id.comment_list);
        //database

        Bundle bundle = this.getActivity().getIntent().getExtras();
        String eventID = bundle.getString("EventID", "");

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.child("EventComment/Event"+eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    String uID = String.valueOf(item.child("User").getValue());
                    String content = String.valueOf(item.child("Content").getValue());
                    String date = String.valueOf(item.child("Date").getValue());
                    String time = String.valueOf(item.child("Time").getValue());
                    UID.add(uID);
                    cmt.add(content);
                    dateList.add(date);
                    timeList.add(time);

                }

                comment  = new String[cmt.size()];
                userID = new String[UID.size()];
                date = new String[dateList.size()];
                time = new String[timeList.size()];

                for (int i = 0 ; i < cmt.size() ; i++){
                    comment[i] = cmt.get(i);
                    userID[i] = UID.get(i);
                    date[i] = dateList.get(i);
                    time[i] = timeList.get(i);

                }
                CustomCommentList customCommentList = new CustomCommentList((Activity) context, userID, comment, date, time);
                commentList.setAdapter(customCommentList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return commentView;
    }

}

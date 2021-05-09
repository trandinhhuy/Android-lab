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

    List<String> avt = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> cmt = new ArrayList<>();


    String[] avatar;
    String[] user_name;
    String[] comment;

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
                    firebaseDatabase.child("User").child(uID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String banned = String.valueOf(snapshot.child("Ban").getValue());
                            if(banned.compareTo("1") != 0){
                                cmt.add(String.valueOf(item.child("Content").getValue()));
                                name.add(String.valueOf(snapshot.child("Name").getValue()));
                                avt.add(uID);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                Toast.makeText(context.getApplicationContext(), "name: "+cmt.size(), Toast.LENGTH_SHORT).show();
                comment  = new String[cmt.size()];
                avatar = new String[cmt.size()];
                user_name = new String[cmt.size()];


                for (int i = 0 ; i < cmt.size() ; i++){
                    comment[i] = cmt.get(i);
                    avatar[i] = avt.get(i);
                    user_name[i] = name.get(i);

                }
                CustomCommentList customCommentList = new CustomCommentList((Activity) context, avatar, user_name, comment);
                commentList.setAdapter(customCommentList);



            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





//                listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(getActivity(), EventInformation.class);
//                        Bundle bundle1 = new Bundle();
//                        bundle1.putString("EventID", adapterID[position]);
//                        intent.putExtras(bundle1);
//                        startActivity(intent);
//                    }
//                });

        return commentView;
    }




}

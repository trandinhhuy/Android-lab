package com.example.everhope.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.everhope.AllEventActivity;
import com.example.everhope.EventInformation;
import com.example.everhope.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    SharedPreferences pref;
    ViewGroup scrollView;
    ImageView icon;
    TextView caption;
    TextView day;
    List<String> idList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    String [] id;
    String [] name;
    String [] date;
    int[] volunteerImages = {R.drawable.volun1, R.drawable.volun2, R.drawable.volun3};

    String [] interestSplit;
    private HomeViewModel homeViewModel;
    Activity activity;
    public static HomeFragment newInstance(SharedPreferences pref, Activity host) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.pref = pref;
        fragment.activity = host;
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView showAll = (TextView) root.findViewById(R.id.activityAll);
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllEventActivity.class);
                startActivity(intent);
            }
        });
        pref = getActivity().getSharedPreferences("myloginpref", Context.MODE_PRIVATE);
        String userID = pref.getString("userID", "-1");

        if (userID.compareTo("-1") == 0){
            return root;
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference().child("User").child(userID);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userInterest = String.valueOf(snapshot.child("Interest").getValue());
                interestSplit = userInterest.split(",");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        myref = firebaseDatabase.getReference().child("Event");

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0; // get 5 popular event
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    int priority = 0;
                    String eventInterest = String.valueOf(dataSnapshot.child("Interest").getValue());
                    for (int i = 0 ; i < interestSplit.length ; i++){
                        if (eventInterest.contains(interestSplit[i])){
                            priority++;

                        }
                    }
                    if (priority >= 2 || priority >= interestSplit.length / 3){
                        count++;
                        idList.add(String.valueOf(dataSnapshot.getKey()));
                        nameList.add(String.valueOf(dataSnapshot.child("Name").getValue()));
                        dateList.add(String.valueOf(dataSnapshot.child("Datetime").getValue()));

                    }
                    if (count >= 5){
                        scrollView = (ViewGroup) root.findViewById(R.id.viewActivity);
                        View singleFrame;
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        for (int i = 0 ; i < idList.size(); i++){
                            StorageReference storageRef = storageReference.child("Event/Event" + idList.get(i));
                            try {
                                singleFrame = getLayoutInflater().inflate(R.layout.activity_view, null);
                                singleFrame.setId(i);
                                try {
                                    View finalSingleFrame = singleFrame;
                                    storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                        @Override
                                        public void onSuccess(ListResult listResult) {
                                            for (StorageReference eventAvatar : listResult.getItems()) {
                                                try {
                                                    final File localFile = File.createTempFile("EventAVT", "jpg");
                                                    eventAvatar.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                                            ImageView eventAvatar = (ImageView) finalSingleFrame.findViewById(R.id.iconImg);
                                                            eventAvatar.setImageBitmap(bitmap);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                        }
                                                    });
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                } catch (IllegalStateException e){

                                }
                                TextView caption = (TextView) singleFrame.findViewById(R.id.caption);
                                TextView dateView = (TextView) singleFrame.findViewById(R.id.date);

                                caption.setText(nameList.get(i));
                                dateView.setText(dateList.get(i));

                                scrollView.addView(singleFrame);
                                int finalI = i;
                                singleFrame.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), EventInformation.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("EventID", idList.get(finalI));
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                            } catch (IllegalStateException e){

                            }



                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;

    }
}
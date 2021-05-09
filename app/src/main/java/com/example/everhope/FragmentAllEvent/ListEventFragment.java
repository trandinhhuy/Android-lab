package com.example.everhope.FragmentAllEvent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.everhope.AllEventActivity;
import com.example.everhope.customlist.CustomEventList;
import com.example.everhope.EventInformation;
import com.example.everhope.R;
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

public class ListEventFragment extends Fragment {
    Context context = null;
    List<String> name = new ArrayList<>();
    List<String> date = new ArrayList<>();
    List<String> eventID = new ArrayList<>();
    List<Bitmap> bitmap = new ArrayList<>();
    String[] adapterName;
    String[] adapterDate;
    String[] adapterID;
    Bitmap[] adapterBitmap;

    public static ListEventFragment newInstance() {
        Bundle args = new Bundle();
        ListEventFragment fragment = new ListEventFragment();
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout fragmentListEvent = (RelativeLayout) inflater.inflate(R.layout.list_event, null, true);
        ListView listEvent = (ListView) fragmentListEvent.findViewById(R.id.list_event);

        Bundle bundle = this.getArguments();
        String topic = bundle.getString("topic", "");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference eventRef = firebaseDatabase.getReference().child("Event");

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    String banned = String.valueOf(item.child("Ban").getValue());
                    String interest = String.valueOf(item.child("Interest").getValue());
                    String eventName = String.valueOf(item.child("Name").getValue());
                    if (banned.compareTo("1") != 0 && (interest.contains(topic) || topic.contains(interest) || eventName.contains(topic) || topic.contains(eventName))) {
                        name.add(String.valueOf(item.child("Name").getValue()));
                        date.add(String.valueOf(item.child("Datetime").getValue()));
                        String ID = String.valueOf(item.getKey());
                        eventID.add(ID);
                    }
                }

                adapterName = new String[eventID.size()];
                adapterDate = new String[eventID.size()];
                adapterID  = new String[eventID.size()];

                for (int i = 0 ; i < eventID.size() ; i++){
                    adapterName[i] = name.get(i);
                    adapterDate[i] = date.get(i);
                    adapterID[i] = eventID.get(i);
                }
                CustomEventList customEventList = new CustomEventList((Activity) context, adapterID, adapterName, adapterDate, R.layout.event_view_full);
                listEvent.setAdapter(customEventList);
                listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), EventInformation.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("EventID", adapterID[position]);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return fragmentListEvent;
    }
}

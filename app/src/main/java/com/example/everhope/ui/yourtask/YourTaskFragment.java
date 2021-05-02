package com.example.everhope.ui.yourtask;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.everhope.AddEventActivity;
import com.example.everhope.AllEventActivity;
import com.example.everhope.EventInformation;
import com.example.everhope.Leaderboard;
import com.example.everhope.MenuActivity;
import com.example.everhope.R;
import com.example.everhope.customlist.CustomCommentList;
import com.example.everhope.customlist.CustomEventList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class YourTaskFragment extends Fragment {
    SharedPreferences pref;
    private YourTaskViewModel yourTaskViewModel;
    Context context = null;
    List<String> nameList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    List<String> idList = new ArrayList<>();

    String[] name;
    String[] date;
    String[] id;
    public static YourTaskFragment newInstance(SharedPreferences pref) {

        Bundle args = new Bundle();

        YourTaskFragment fragment = new YourTaskFragment();
        fragment.pref = pref;
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
        } catch (IllegalStateException e){

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourTaskViewModel =
                new ViewModelProvider(this).get(YourTaskViewModel.class);
        View root = inflater.inflate(R.layout.your_task_view, null, true);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference yourTask = firebaseDatabase.getReference().child("EventParticipant");
        String userID = MenuActivity.getMyLoginPref(getActivity());

        yourTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    for (DataSnapshot user : item.getChildren()){
                        String user1 = String.valueOf(user.getKey());
                        if (user1.compareTo(userID) == 0){
                            String event = String.valueOf(item.getKey());
                            String split[] = event.split("t");
                            String eventID = split[1];
                            idList.add(eventID);
                            break;
                        }
                    }
                }
                FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
                DatabaseReference eventInformation = firebaseDatabase1.getReference().child("Event");
                eventInformation.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String totalTask = String.valueOf(idList.size());
                        TextView numberTask = (TextView) root.findViewById(R.id.number_task);
                        numberTask.setText(totalTask + " Tasks");
                        name = new String[idList.size()];
                        date = new String[idList.size()];
                        id = new String[idList.size()];
                        for (DataSnapshot item1 : snapshot.getChildren()){
                            for (int i = 0 ; i < idList.size() ; i++){
                                if (idList.get(i).compareTo(String.valueOf(item1.getKey())) == 0){
                                    name[i] = String.valueOf(item1.child("Name").getValue());
                                    date[i] = String.valueOf(item1.child("Datetime").getValue());
                                    id[i] = idList.get(i);
                                }
                            }
                        }
                        ListView listView = root.findViewById(R.id.list_your_task);
                        CustomEventList customEventList = new CustomEventList((Activity)context, id, name, date, R.layout.event_view_full);
                        listView.setAdapter(customEventList);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getActivity(), EventInformation.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("EventID", idList.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ListView listView = root.findViewById(R.id.list_your_task);
        FloatingActionButton floatingActionButton = root.findViewById(R.id.add_event);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                startActivity(intent);
            }
        });
        /*CustomEventList customEventList = new CustomEventList((Activity)context, images, topics, members, R.layout.event_view_full);
        listView.setAdapter(customEventList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventInformation.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", "id");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
         */
        return root;
    }
}
package com.example.everhope.FragmentAllEvent;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.everhope.AllEventActivity;
import com.example.everhope.CustomEventList;
import com.example.everhope.EventInformation;
import com.example.everhope.R;

public class ListEventFragment extends Fragment {
    AllEventActivity allEventActivity;
    Context context = null;
    Integer [] images = {R.drawable.volun1, R.drawable.volun2, R.drawable.volun3};
    int[] members = {12, 11, 15};
    String[] topics = {"1", "v", "a"};

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
        LinearLayout fragmentListEvent = (LinearLayout) inflater.inflate(R.layout.list_event, null, true);
        ListView listEvent = (ListView) fragmentListEvent.findViewById(R.id.list_event);
        Bundle bundle = getArguments();
        String topic = bundle.getString("topic", "");
        CustomEventList customEventList = new CustomEventList((Activity) context, images, topics, members);
        listEvent.setAdapter(customEventList);
        listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventInformation.class);
                startActivity(intent);
            }
        });

        return fragmentListEvent;
    }
}

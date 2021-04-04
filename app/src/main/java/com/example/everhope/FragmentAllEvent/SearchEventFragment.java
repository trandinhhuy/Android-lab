package com.example.everhope.FragmentAllEvent;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.everhope.AllEventActivity;
import com.example.everhope.R;

public class SearchEventFragment extends Fragment {
    ViewGroup topicButtons;
    AllEventActivity allEventActivity;
    Context context = null;
    String [] topics = {"cleaning", "charity", "raising"};

    public static SearchEventFragment newInstance() {

        Bundle args = new Bundle();

        SearchEventFragment fragment = new SearchEventFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout fragmentSearchEvent = (LinearLayout) inflater.inflate(R.layout.search_event, null, true);
        topicButtons = (ViewGroup) fragmentSearchEvent.findViewById(R.id.button_topic);
        for (int i = 0 ; i < topics.length ; i++){
            final View btnView = getLayoutInflater().inflate(R.layout.button_topic, null, true);
            btnView.setId(i);
            Button topBtn = (Button) btnView.findViewById(R.id.buttonTopicView);
            topBtn.setText(topics[i]);
            topicButtons.addView(btnView);
            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return fragmentSearchEvent;
    }
}

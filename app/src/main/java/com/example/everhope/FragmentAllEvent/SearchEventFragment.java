package com.example.everhope.FragmentAllEvent;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.everhope.AllEventActivity;
import com.example.everhope.R;

public class SearchEventFragment extends Fragment {
    ViewGroup topicButtons;
    Context context = null;
    String [] topics = {"Homeless", "Health", "People", "Children", "Education", "Animals", "Broadcasting", "Sport", "Environment"};

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
            int finalI = i;
            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("topic", topics[finalI]);
                    ListEventFragment listEventFragment = new ListEventFragment();
                    listEventFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.list_event_fragment, listEventFragment).commit();
                }
            });
        }
        ImageView searchTopic = (ImageView) fragmentSearchEvent.findViewById(R.id.search_onclick);
        searchTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText topicInput = (EditText) fragmentSearchEvent.findViewById(R.id.search_topic);
                String template = topicInput.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("topic", template);
                ListEventFragment listEventFragment = new ListEventFragment();
                listEventFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.list_event_fragment, listEventFragment).commit();
                topicInput.setText("");
            }
        });
        return fragmentSearchEvent;
    }
}

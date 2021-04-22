package com.example.everhope.ui.yourtask;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
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
import com.example.everhope.R;
import com.example.everhope.customlist.CustomEventList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class YourTaskFragment extends Fragment {
    private YourTaskViewModel yourTaskViewModel;
    Context context = null;
    Integer [] images = {R.drawable.volun1, R.drawable.volun2, R.drawable.volun3};
    int[] members = {12, 11, 15};
    String[] topics = {"1", "v", "a"};

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
        ListView listView = root.findViewById(R.id.list_your_task);
        FloatingActionButton floatingActionButton = root.findViewById(R.id.add_event);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                startActivity(intent);
            }
        });
        CustomEventList customEventList = new CustomEventList((Activity)context, images, topics, members, R.layout.event_view_full);
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
        return root;
    }
}
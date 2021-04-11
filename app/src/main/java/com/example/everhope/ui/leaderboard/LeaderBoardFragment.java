package com.example.everhope.ui.leaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.everhope.Adapter;
import com.example.everhope.Leaderboard;
import com.example.everhope.R;
import com.example.everhope.ui.profile.ProfileViewModel;

public class LeaderBoardFragment extends Fragment implements View.OnClickListener {
    private LeaderBoardViewModel leaderBoardViewModel;
    RecyclerView recyclerView;
    Adapter adapter;
    TextView btn_account, btn_event;
    String[] names, contents, ranks;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaderBoardViewModel =
                new ViewModelProvider(this).get(LeaderBoardViewModel.class);
        root = inflater.inflate(R.layout.leaderboard, container, false);
        btn_account = (TextView) root.findViewById(R.id.btn_account);
        btn_event = (TextView) root.findViewById(R.id.btn_event);
        btn_account.setOnClickListener(this);
        btn_event.setOnClickListener(this);

        names = getResources().getStringArray(R.array.list_name);
        contents = getResources().getStringArray(R.array.list_content);
        ranks = getResources().getStringArray(R.array.list_rank);

        recyclerView = root.findViewById(R.id.rankList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext(),names, contents, ranks);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_account:
                btn_account.setTextColor(getResources().getColor(R.color.bg));
                btn_event.setTextColor(getResources().getColor(R.color.black));
                names = getResources().getStringArray(R.array.list_name);
                contents = getResources().getStringArray(R.array.list_content);
                ranks = getResources().getStringArray(R.array.list_rank);
                break;
            case R.id.btn_event:
                btn_event.setTextColor(getResources().getColor(R.color.bg));
                btn_account.setTextColor(getResources().getColor(R.color.black));
                names = getResources().getStringArray(R.array.list_name_event);
                contents = getResources().getStringArray(R.array.list_content_event);
                ranks = getResources().getStringArray(R.array.list_rank_event);
                break;

        }
        recyclerView = root.findViewById(R.id.rankList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext(),names, contents, ranks);
        recyclerView.setAdapter(adapter);
    }
}

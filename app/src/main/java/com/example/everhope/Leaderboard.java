package com.example.everhope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

public class Leaderboard extends Activity {

    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        String[] names = getResources().getStringArray(R.array.list_name);
        String[] contents = getResources().getStringArray(R.array.list_content);
        String[] ranks = getResources().getStringArray(R.array.list_rank);

        recyclerView = findViewById(R.id.rankList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,names, contents, ranks);
        recyclerView.setAdapter(adapter);
    }
}
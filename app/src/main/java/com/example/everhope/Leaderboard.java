package com.example.everhope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Leaderboard extends Activity implements View.OnClickListener {

    RecyclerView recyclerView;
    Adapter adapter;
    TextView btn_account, btn_event;
    String[] names, contents, ranks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        btn_account = (TextView) findViewById(R.id.btn_account);
        btn_event = (TextView) findViewById(R.id.btn_event);
        btn_account.setOnClickListener(this);
        btn_event.setOnClickListener(this);

        btn_account.setTextColor(getColor(R.color.bg));
        names = getResources().getStringArray(R.array.list_name);
        contents = getResources().getStringArray(R.array.list_content);
        ranks = getResources().getStringArray(R.array.list_rank);

        recyclerView = findViewById(R.id.rankList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,names, contents, ranks);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_account:
                btn_account.setTextColor(getColor(R.color.bg));
                btn_event.setTextColor(getColor(R.color.black));
                names = getResources().getStringArray(R.array.list_name);
                contents = getResources().getStringArray(R.array.list_content);
                ranks = getResources().getStringArray(R.array.list_rank);
                break;
            case R.id.btn_event:
                btn_event.setTextColor(getColor(R.color.bg));
                btn_account.setTextColor(getColor(R.color.black));
                names = getResources().getStringArray(R.array.list_name_event);
                contents = getResources().getStringArray(R.array.list_content_event);
                ranks = getResources().getStringArray(R.array.list_rank_event);
                break;

        }
        recyclerView = findViewById(R.id.rankList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,names, contents, ranks);
        recyclerView.setAdapter(adapter);
    }
}
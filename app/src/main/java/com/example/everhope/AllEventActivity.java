package com.example.everhope;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.everhope.FragmentAllEvent.ListEventFragment;
import com.example.everhope.FragmentAllEvent.SearchEventFragment;

public class AllEventActivity extends FragmentActivity {
    FragmentTransaction ft;
    ListEventFragment listEventFragment;
    SearchEventFragment searchEventFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.all_event_tool_bar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ft = getSupportFragmentManager().beginTransaction();
        listEventFragment = ListEventFragment.newInstance();
        ft.replace(R.id.list_event_fragment, listEventFragment);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        searchEventFragment = SearchEventFragment.newInstance();
        ft.replace(R.id.search_event_fragment, searchEventFragment);
        ft.commit();
    }
}
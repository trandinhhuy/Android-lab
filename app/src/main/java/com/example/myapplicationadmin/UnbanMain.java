package com.example.myapplicationadmin;


import android.os.Bundle;

import com.example.myapplicationadmin.ui.main.ReportedPagerAdapter;
import com.example.myapplicationadmin.ui.main.UnbanPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class UnbanMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unban_main);

        UnbanPagerAdapter adt = new UnbanPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager_unban);
        viewPager.setAdapter(adt);

        TabLayout tabs = findViewById(R.id.tabs_unban);
        tabs.setupWithViewPager(viewPager);
    }
}
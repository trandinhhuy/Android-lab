package com.example.everhope;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class AdminReportMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_rp_main);





        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_tool_rp_main);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ReportedPagerAdapter sectionsPagerAdapter = new ReportedPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.rp_main_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.rp_main_tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
package com.example.everhope;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.everhope.FragmentAdminReport.AdminReportListFragment;
import com.example.everhope.FragmentAdminReport.AdminReportTabsFragment;
import com.google.android.material.tabs.TabLayout;

public class AdminReportMain extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_rp_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_tool_rp_main);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getFragmentManager().beginTransaction().replace(R.id.admin_report_tabs, new AdminReportTabsFragment()).commit();
        getFragmentManager().beginTransaction().replace(R.id.admin_report_list, new AdminReportListFragment()).commit();
    }
}
package com.example.everhope.FragmentAdminReport;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.everhope.R;

public class AdminReportTabsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_report_tabs, null, true);
        TextView eventTab = (TextView) root.findViewById(R.id.tabs_report_event);
        TextView userTab = (TextView) root.findViewById(R.id.tabs_report_user);

        eventTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.admin_report_list, new AdminReportListFragment()).commit();
            }
        });
        userTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.admin_report_list, new AdminReportListFragment()).commit();
            }
        });

        return root;
    }
}

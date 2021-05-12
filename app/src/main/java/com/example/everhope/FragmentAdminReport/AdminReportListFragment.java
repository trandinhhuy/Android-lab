package com.example.everhope.FragmentAdminReport;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.everhope.R;

public class AdminReportListFragment extends Fragment {

    public static AdminReportListFragment newInstance() {

        Bundle args = new Bundle();

        AdminReportListFragment fragment = new AdminReportListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_list_fragment, null, true);
        ListView reportMainList = (ListView) root.findViewById(R.id.admin_rp_main_list);
        
        return root;
    }
}

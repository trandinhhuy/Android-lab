package com.example.everhope.FragmentAdminReport;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.everhope.R;

import java.util.ArrayList;
import java.util.List;

public class AdminUserReportListFragment extends Fragment {
    List<String> reportIdlst = new ArrayList<>();
    List<String> reportDateLst = new ArrayList<>();
    List<String> reportTimeLst = new ArrayList<>();
    List<String> reportDetailLst = new ArrayList<>();
    List<String> reportReasonLst = new ArrayList<>();
    List<String> reportByLst = new ArrayList<>();
    List<String> reportedLst = new ArrayList<>();

    String[] reportDate;
    String[] reportTime;
    String[] reportDetail;
    String[] reportReason;
    String[] reportBy;
    String[] reported;
    String[] reportID;

    public static AdminUserReportListFragment newInstance() {

        Bundle args = new Bundle();

        AdminUserReportListFragment fragment = new AdminUserReportListFragment();
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

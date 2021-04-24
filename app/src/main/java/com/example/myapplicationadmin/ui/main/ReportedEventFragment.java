package com.example.myapplicationadmin.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplicationadmin.R;

public class ReportedEventFragment extends Fragment {
    String[] rpDatetime ={
            "09:50 15/08/2020",
            "12:50 16/08/2020",
            "10:50 17/08/2020",
            "05:50 18/08/2020",
            "07:50 19/08/2020"
    };
    String[] rpDetail ={
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ",
            "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text.",
            "The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.",
            "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.",
            "Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."
    };
    String[] rpReason ={
            "Reason 1",
            "Reason 2",
            "Reason 3",
            "Reason 4",
            "Reason 5"
    };
    int[] rpID ={1,2,3,4,5};
    int type_event = 1;




    public ReportedEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.list_fragment, container, false);
        ListView list = (ListView)v.findViewById(R.id.main_list);
        list.setAdapter(new ReportedItemAdapter(getActivity(),rpDatetime,rpReason,rpDetail,rpID,type_event));

        return v;
    }
}
package com.example.everhope;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportedEventFragment extends Fragment {

    int type_user = 1;

    public ReportedEventFragment() {
    }

    ArrayList<ReportedObject> lst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.admin_list_fragment, container, false);
        lst = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("EventReport");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot item : snapshot.getChildren()){
                    ReportedObject addNew = new ReportedObject();
                    addNew.reason = String.valueOf(item.child("Reason").getValue());
                    addNew.date = String.valueOf(item.child("Date").getValue());
                    addNew.time = String.valueOf(item.child("Time").getValue());
                    addNew.reportBy=String.valueOf(item.child("ReportedBy").getValue());
                    addNew.reported = String.valueOf(item.child("Reported").getValue());
                    addNew.detail=String.valueOf(item.child("Detail").getValue());
                    addNew.key = String.valueOf(item.child("Key").getValue());
                    if (addNew!=null) lst.add(addNew);
                }

                if (lst.size()>0){
                    String[]title = new String[lst.size()];
                    for (int i =0; i < lst.size();i++) title[i]=lst.get(i).reason;
                    ListView list = (ListView)v.findViewById(R.id.admin_rp_main_list);
                    list.setAdapter(new ReportedItemAdapter(getActivity(),type_user,lst,title));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        return v;
    }
}

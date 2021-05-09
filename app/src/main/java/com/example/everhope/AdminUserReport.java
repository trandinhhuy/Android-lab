package com.example.everhope;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AdminUserReport extends AppCompatActivity {

    ArrayList<ReportedObject> lst = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_rp_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_tool_rp_main);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("UserReport");
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

                    if (addNew!=null) {
                        lst.add(addNew);
                    }
                }

                if (lst.size() > 0) {
                    String[] title = new String[lst.size()];
                    for (int i = 0; i < lst.size(); i++) {
                        title[i] = lst.get(i).reason;
                    }

                    AdminReportAdapter adminReportAdapter = new AdminReportAdapter(AdminUserReport.this,2,lst,title);
                    listView = (ListView)findViewById(R.id.admin_rp_list);
                    listView.setAdapter(adminReportAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
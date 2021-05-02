package com.example.everhope;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.everhope.supportClass.UpdateFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportActivity extends AppCompatActivity {
    TextView eventName, eventDescription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_event_layout);

        getSupportActionBar().hide();
        Toolbar reportEventToolBar = (Toolbar) findViewById(R.id.report_event_tool_bar);
        reportEventToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        eventName = (TextView) findViewById(R.id.report_event_name);
        eventDescription = (TextView) findViewById(R.id.report_event_description);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String eventID = bundle.getString("EventID", "");
        String organizer = bundle.getString("EventName", "");

        eventName.setText(organizer);

        // try to update exists report
        UpdateFirebase.updateData("EventReport/Event0/0/Detail", "test report");

        // add new report
        UpdateFirebase.updateData("EventReport/Event2/0/Detail", "New report");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reportRef = firebaseDatabase.getReference().child("Event/" + eventID);
        reportRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String description = String.valueOf(snapshot.child("Description").getValue());
                eventDescription.setText(description);
                EventInformation.setImage("Event/Event" + eventID, R.id.report_event_image, getWindow().getDecorView());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

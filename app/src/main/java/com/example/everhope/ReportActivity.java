package com.example.everhope;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportActivity extends AppCompatActivity {
    TextView eventName, eventDescription, btn_submit;
    CheckBox cbIllegal, cbSpam, cbOffensive, cbAbuse, cbSolicitation, cbOthers;
    EditText detail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_event_layout);

        String userID = MenuActivity.getMyLoginPref(getApplicationContext());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String eventID = bundle.getString("EventID", "");
        String organizer = bundle.getString("EventName", "");

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
        btn_submit = (TextView) findViewById(R.id.report_event_submit);
        cbIllegal = (CheckBox) findViewById(R.id.report_event_illegal_event);
        cbSpam= (CheckBox) findViewById(R.id.report_event_spam_event);
        cbOffensive= (CheckBox) findViewById(R.id.report_event_offensive_event);
        cbAbuse= (CheckBox) findViewById(R.id.report_event_abuse_event);
        cbSolicitation= (CheckBox) findViewById(R.id.report_event_solicitation_event);
        cbOthers= (CheckBox) findViewById(R.id.report_event_other_reason);
        detail = (EditText) findViewById(R.id.report_event_detail);





        eventName.setText(organizer);


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

package com.example.everhope;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.everhope.supportClass.UpdateFirebase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ReportUser extends AppCompatActivity {
    TextView  btn_submit;
    CheckBox cbIllegal, cbSpam, cbOffensive, cbAbuse, cbSolicitation, cbOthers;
    EditText detail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_user_layout);

        String userID = MenuActivity.getMyLoginPref(getApplicationContext());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String rpUserID = bundle.getString("ReportUserID", "");

        getSupportActionBar().hide();
        Toolbar reportEventToolBar = (Toolbar) findViewById(R.id.report_user_tool_bar);
        reportEventToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_submit = (TextView) findViewById(R.id.report_user_submit);
        cbIllegal = (CheckBox) findViewById(R.id.report_user_illegal_event);
        cbSpam= (CheckBox) findViewById(R.id.report_user_spam_event);
        cbOffensive= (CheckBox) findViewById(R.id.report_user_offensive_event);
        cbAbuse= (CheckBox) findViewById(R.id.report_user_abuse_event);
        cbSolicitation= (CheckBox) findViewById(R.id.report_user_solicitation_event);
        cbOthers= (CheckBox) findViewById(R.id.report_user_other_reason);
        detail = (EditText) findViewById(R.id.report_user_detail);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportedObject re = new ReportedObject();
                re.detail = detail.getText().toString();
                if (cbSpam.isChecked()) re.reason+= cbSpam.getText().toString()+"   ";
                if (cbAbuse.isChecked()) re.reason+= cbAbuse.getText().toString()+"   ";
                if (cbIllegal.isChecked()) re.reason+= cbIllegal.getText().toString()+"   ";
                if (cbOffensive.isChecked()) re.reason+= cbOffensive.getText().toString()+"   ";
                if (cbSolicitation.isChecked()) re.reason+= cbSolicitation.getText().toString()+"   ";

                if (re.reason.length()<1 && re.detail.length()<1){
                    Toast.makeText(getApplicationContext(), "Please complete all needed fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (re.reason.length()<1 && re.detail.length()>0 || cbOthers.isChecked()){
                    re.reason+= cbOthers.getText().toString()+"   ";
                }
                String [] datetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()).split(" ");
                re.date = datetime[0];
                re.time = datetime[1];
                re.userID = userID;

                UpdateFirebase.updateReportedEvent("UserReport/User"+rpUserID, re);
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}

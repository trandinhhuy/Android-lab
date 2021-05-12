package com.example.everhope;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AdminHomepage extends Activity {
    LinearLayout account, us_report, us_ban, logout, ev_report, ev_ban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_hompage);

        String userID = MenuActivity.getMyLoginPref(getApplicationContext());

        account = (LinearLayout) findViewById(R.id.admin_home_account);
        logout = (LinearLayout) findViewById(R.id.admin_home_logout);
        us_report = (LinearLayout) findViewById(R.id.admin_home_user_req);
        us_ban = (LinearLayout) findViewById(R.id.admin_home_user_ban);
        ev_report = (LinearLayout) findViewById(R.id.admin_home_event_req);
        ev_ban = (LinearLayout) findViewById(R.id.admin_home_event_ban);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), userProfile.class);
                Bundle profileBundle = new Bundle();
                profileBundle.putString("UserID", userID);
                profileIntent.putExtras(profileBundle);
                startActivity(profileIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myloginpref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isLogin", false);
                editor.putString("userID", "-1");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        ev_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdminEventReport.class);
                startActivity(i);
            }
        });

        us_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdminUserReport.class);
                startActivity(i);
            }
        });

/*
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), AdminReportMain.class);
                startActivity(profileIntent);
            }
        });*/


    }
}
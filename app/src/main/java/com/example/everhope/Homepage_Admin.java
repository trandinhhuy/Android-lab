package com.example.everhope;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Homepage_Admin extends Activity {
    LinearLayout account, report, ban, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_hompage);

        String userID = MenuActivity.getMyLoginPref(getApplicationContext());

        account = (LinearLayout) findViewById(R.id.admin_home_account);
        report = (LinearLayout) findViewById(R.id.admin_home_req);
        logout = (LinearLayout) findViewById(R.id.admin_home_logout);
        ban = (LinearLayout) findViewById(R.id.admin_home_ban);

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

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), AdminReportMain.class);
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

    }
}
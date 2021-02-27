package com.example.registerform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class resultform extends AppCompatActivity {
    public static final String STATUS = "com.example.registerform.status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String username = intent.getStringExtra(registerform.EXTRA_username);
        String password = intent.getStringExtra(registerform.EXTRA_password);
        String birthday = intent.getStringExtra(registerform.EXTRA_birthday);
        String gender = intent.getStringExtra(registerform.EXTRA_gender);
        String hobbies = intent.getStringExtra(registerform.EXTRA_hobbies);

        TextView usernameView = (TextView) findViewById(R.id.usernameView);
        TextView passwordView = (TextView) findViewById(R.id.passwordView);
        TextView birthdayView = (TextView) findViewById(R.id.birthdayView);
        TextView genderView = (TextView) findViewById(R.id.genderView);
        TextView hobbiesView = (TextView) findViewById(R.id.hobbiesView);

        usernameView.setText(username);
        passwordView.setText(password);
        birthdayView.setText(birthday);
        genderView.setText(gender);
        hobbiesView.setText(hobbies);

        Button exitBtn = (Button) findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), registerform.class);
                intent1.setFlags(intent1.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("EXIT", true);
                startActivity(intent1);
            }
        });
    }
}
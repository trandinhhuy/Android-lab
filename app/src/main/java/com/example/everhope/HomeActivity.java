package com.example.everhope;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import static com.example.everhope.R.id.toolMenu;

public class HomeActivity extends AppCompatActivity {
    ViewGroup scrollView;
    ImageView icon;
    TextView caption;
    TextView day;

    NavigationView navigationView;
    Toolbar toolbar;
    ScrollView scrollHome;
    ImageView imageView;
    DrawerLayout drawerLayout;

    String[] items = {"Nhat rac", "Don bien", "Trong cay"};
    int[] volunteerImages = {R.drawable.volun1, R.drawable.volun2, R.drawable.volun3};
    String[] date = {"25/3/2021", "24/3/2021", "23/3/2021"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(toolMenu);

        scrollView = (ViewGroup) findViewById(R.id.viewActivity);
        for (int i = 0 ; i < items.length ; i++){
            final View singleFrame = getLayoutInflater().inflate(R.layout.activity_view, null);
            singleFrame.setId(i);
            ImageView icon = (ImageView) singleFrame.findViewById(R.id.iconImg);
            TextView caption = (TextView) singleFrame.findViewById(R.id.caption);
            TextView dateView = (TextView) singleFrame.findViewById(R.id.date);

            icon.setImageResource(volunteerImages[i]);
            caption.setText(items[i]);
            dateView.setText(date[i]);

            scrollView.addView(singleFrame);
            singleFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
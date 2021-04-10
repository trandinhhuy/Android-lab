package com.example.everhope;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ViewPager screenPager;
    IntroAdapter introAdapter;
    TabLayout tabIndicator;
    Button btnNext;

    int position = 0;
    Button buttonnGetstarted;
    Animation btnAnim;
    TextView txtSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_intro);
        btnNext = (Button) findViewById(R.id.buttonNext);
        buttonnGetstarted = (Button) findViewById(R.id.buttonStart);
        tabIndicator = (TabLayout) findViewById(R.id.tabLayout);

        List<IntroItem> mList = new ArrayList<>();
        mList.add(new IntroItem("Môi trường làm việc công bằng\n Dễ dàng quản lý cá nhân, thành viên.", "Dễ dàng quản lý", R.drawable.intro1));
        mList.add(new IntroItem("Growing", "b", R.drawable.intro2));
        mList.add(new IntroItem("Washing", "n", R.drawable.intro3));

        screenPager = (ViewPager) findViewById(R.id.introView);
        introAdapter = new IntroAdapter(this, mList);
        screenPager.setAdapter(introAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size() - 1){
                    loadLastScreen();
                }
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        buttonnGetstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(getApplicationContext(), SignIn.class);
                startActivity(signIn);
                //Intent mainActivity = new Intent(getApplicationContext(), MenuActivity.class);
                //startActivity(mainActivity);
                savePrefsData();
                finish();
            }
        });
    }
    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpened",false);
        return  isIntroActivityOpnendBefore;
    }

    private void savePrefsData(){
        SharedPreferences pref = getApplication().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }
    private void loadLastScreen(){
        btnNext.setVisibility(View.INVISIBLE);
        buttonnGetstarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        buttonnGetstarted.setAnimation(btnAnim);
    }
}
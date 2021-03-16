package com.example.bt5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements MainCallBack {
    FragmentTransaction ft;
    FragmentRed redFragment;
    FragmentBlue blueFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ft = getSupportFragmentManager().beginTransaction();
        blueFragment = FragmentBlue.newInstance("first-blue");
        ft.replace(R.id.ListView, blueFragment);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        redFragment = FragmentRed.newInstance("first-red");
        ft.replace(R.id.Information, redFragment);
        ft.commit();

    }

    @Override
    public void onMsgFromFragToMain(String sender, String value) {
        Toast.makeText(getApplication(),
                "Main got " + sender + "\n" + value, Toast.LENGTH_LONG
                ).show();
        if (sender.equals("RED_FRAG")){

        }
        if (sender.equals("BLUE_FRAG")){
            redFragment.onMsgFromMainToFragment("sender: " + sender + "\nMsg: " + value);
        }
    }
}
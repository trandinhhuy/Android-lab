package com.example.everhope;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;

import com.example.everhope.FragmentAllEvent.ListEventFragment;
import com.example.everhope.ui.home.HomeFragment;
import com.example.everhope.ui.leaderboard.LeaderBoardFragment;
import com.example.everhope.ui.profile.ProfileFragment;
import com.example.everhope.ui.yourtask.YourTaskFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    public static SQLiteDatabase db;
    public static SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ImageView menu_icon = findViewById(R.id.menu_icon);
        File storagePath = getApplication().getFilesDir();
        String myDBPath = storagePath +"/" + "EverHope";

        try {
            db = SQLiteDatabase.openDatabase(myDBPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            db = openOrCreateDatabase("EverHope", MODE_PRIVATE, null);
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        pref = getApplication().getSharedPreferences("myloginpref", MODE_PRIVATE);
        int userID = pref.getInt("userID", -1);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_personalprofile, R.id.nav_yourtask, R.id.nav_leaderboard, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new HomeFragment()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new HomeFragment()).commit();
        }
        if (id == R.id.nav_profile){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, ProfileFragment.newInstance(db, pref)).commit();
        }
        if (id == R.id.nav_event){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new YourTaskFragment()).commit();
        }
        if (id == R.id.nav_leaderboard){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new LeaderBoardFragment()).commit();
        }
        if (id == R.id.nav_logout){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("myloginpref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isLogin", false);
            editor.putInt("userID", -1);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
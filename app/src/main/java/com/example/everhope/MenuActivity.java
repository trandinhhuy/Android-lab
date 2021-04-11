package com.example.everhope;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

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

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolMenu);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_personalprofile, R.id.nav_yourtask, R.id.nav_leaderboard, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ProfileFragment()).commit();
        }
        if (id == R.id.nav_event){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new YourTaskFragment()).commit();
        }
        if (id == R.id.nav_leaderboard){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new LeaderBoardFragment()).commit();
        }
        if (id == R.id.nav_logout){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new YourTaskFragment()).commit();
        }
        return true;
    }
}
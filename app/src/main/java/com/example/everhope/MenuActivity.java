package com.example.everhope;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.everhope.FragmentAllEvent.ListEventFragment;
import com.example.everhope.ui.home.HomeFragment;
import com.example.everhope.ui.leaderboard.LeaderBoardFragment;
import com.example.everhope.ui.profile.ProfileFragment;
import com.example.everhope.ui.yourtask.YourTaskFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.IOException;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    public static SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ImageView menu_icon = findViewById(R.id.menu_icon);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        pref = getApplication().getSharedPreferences("myloginpref", MODE_PRIVATE);
        String userID = pref.getString("userID", "-1");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String node = "User/" + userID;
        DatabaseReference myref = database.getReference().child(node);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String banned = String.valueOf(snapshot.child("Ban").getValue());
                if (banned.compareTo("1") == 0){
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("myloginpref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isLogin", false);
                    editor.putString("userID", "-1");
                    editor.commit();
                    showDialog("Your account has been banned");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        pref = getApplication().getSharedPreferences("myloginpref", MODE_PRIVATE);
        int id = item.getItemId();
        if (id == R.id.nav_home){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, HomeFragment.newInstance(pref)).commit();
        }
        if (id == R.id.nav_profile){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, ProfileFragment.newInstance(pref)).commit();
        }
        if (id == R.id.nav_event){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, YourTaskFragment.newInstance(pref)).commit();
        }
        if (id == R.id.nav_leaderboard){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new LeaderBoardFragment()).commit();
        }
        if (id == R.id.nav_logout){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("myloginpref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isLogin", false);
            editor.putString("userID", "-1");
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
    public void showDialog(String dialogMessage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public static String getMyLoginPref(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("myloginpref", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userID", "");
        return  userId;
    }
}
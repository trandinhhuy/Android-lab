package com.example.bt5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    FragmentTransaction ft;
    FragmentRed redFragment;
    FragmentBlue blueFragment;
    SQLiteDatabase MyDb;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "StudentManagement";
        try {
            db = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            db = openOrCreateDatabase("StudentManagement", MODE_PRIVATE, null);

        } catch (SQLiteException e) {

        }
        db.execSQL("drop table if exists Student");
        db.execSQL("drop table if exists Course");

        db.execSQL("Create table if not exists Course(Malop char primary key,Tenlop Char);");
        db.execSQL("Create table if not exists Student(Maso Char primary key,Avatar int,Hoten Nvarchar,Malop Char,Dtb String, foreign key (Malop) references Course(Malop));");
        String[] args = {String.valueOf(R.drawable.pic1)};

        db.execSQL("insert into Course VALUES('A1', 'Lop 1');");
        db.execSQL("insert into Course VALUES('A2', 'Lop 2');");
        db.execSQL("insert into Course VALUES('A0', 'Lop Putin');");

        db.execSQL("insert into Student VALUES('A1_9289', " + R.drawable.pic1 + ", 'Thai Quynh Mai', 'A1', '9')");
        db.execSQL("insert into Student VALUES('A1_1809', " + R.drawable.pic2 + ", 'Le Thi A', 'A1', '8')");
        db.execSQL("insert into Student VALUES('A2_3509', " + R.drawable.pic3 + ", 'Tran Dinh Huy', 'A2', '10')");
        db.execSQL("insert into Student VALUES('A2_3100', " + R.drawable.pic4 + ", 'Thai Quynh Ni', 'A2', '9')");
        db.execSQL("insert into Student VALUES('A1_1120', " + R.drawable.pic5 + ", 'Vladimir Putin', 'A0', '9')");

        ft = getSupportFragmentManager().beginTransaction();
        blueFragment = FragmentBlue.newInstance("first-blue", db);
        ft.replace(R.id.ListView, blueFragment);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        redFragment = FragmentRed.newInstance("first-red");
        ft.replace(R.id.Information, redFragment);
        ft.commit();

    }

}
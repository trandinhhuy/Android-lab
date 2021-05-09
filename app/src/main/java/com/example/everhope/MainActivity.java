package com.example.everhope;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    private static int Splash_time_out = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        File storagePath = getApplication().getFilesDir();
        String myDBPath = storagePath +"/" + "EverHope";
        try {
            db = SQLiteDatabase.openDatabase(myDBPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            db = openOrCreateDatabase("EverHope", MODE_PRIVATE, null);
        } catch (SQLiteException e){

        }

        db.execSQL("drop table if exists Account");
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Event");
        db.execSQL("drop table if exists Comment");
        db.execSQL("drop table if exists Participant");
        db.execSQL("drop table if exists Interest");
        db.execSQL("drop table if exists Topic");

        //drop table
        String pwd = "123456789a";
        String sha = SHA256.toSHA(pwd);
        db.execSQL("create table if not exists Account(email char primary key, password char);");
        db.execSQL("create table if not exists User (id INTEGER primary key, email char, name varchar, DoB char, gender varchar, Phone varchar(10) , Description varchar, interest varchar);");
        db.execSQL("create table if not exists Event (id INTEGER primary key, hostID integer, name varchar, description varchar, date varchar, time varchar, location varchar, latitude varchar, longitude varchar, foreign key (hostID) references User(id));");
        db.execSQL("create table if not exists Comment (eventID INTEGER, userID INTEGER, comment varchar, commentDate varchar, commentTime varchar, foreign key (eventID) references Event(id), foreign key (userID) references User(id));");
        db.execSQL("create table if not exists Participant (eventID INTEGER, userID INTEGER, foreign key (eventID) references Event(id), foreign key (userID) references User(id));");
        db.execSQL("create table if not exists Topic (id INTEGER primary key, name varchar);");
        db.execSQL("create table if not exists Interest (eventID INTEGER, topicID INTEGER, primary key(eventID, topicID), foreign key(eventID) references Event(id), foreign key(topicID) references Topic(id));");

        db.execSQL("insert into Account VALUES('trandinhhuy@gmail.com', '" + sha + "')");
        db.execSQL("insert into Account VALUES('thaiquynhmai@gmail.com', '" + sha + "')");
        db.execSQL("insert into Account VALUES('tranminhanh@gmail.com', '" + sha + "')");
        db.execSQL("insert into Account VALUES('putin@gmail.com', '" + sha + "')");
        db.execSQL("insert into Account VALUES('jongun@gmail.com', '" + sha + "')");

        db.execSQL("insert into User VALUES(0,'trandinhhuy@gmail.com', 'Tran Dinh Huy', '13/09/2000', 'male', '0889712673', 'expert in fuding', 'fps game')");
        db.execSQL("insert into User VALUES(1,'thaiquynhmai@gmail.com', 'Thai Quynh Mai', '03/01/2000', 'female', '0832674232', 'expert in design', 'cloth game')");
        db.execSQL("insert into User VALUES(2,'tranminhanh@gmail.com', 'Tran Minh Anh', '03/09/2000', 'female', '08472847993', 'adaptable', 'no game')");
        db.execSQL("insert into User VALUES(3,'putin@gmail.com', 'Vladimir Putin', '13/09/1988', 'male', '0984673563', 'expert goverment', 'vokda collector')");
        db.execSQL("insert into User VALUES(4,'jongun@gmail.com', 'Kim Jong Un', '23/2/1988', 'male', '0932764637', 'expert in nuclear bomb', 'aircraft game')");

        db.execSQL("insert into Event VALUES(0, 0, 'Feeding pigeon', 'Feeding pigeon with breads and coke','23/4/2021', '18:00', 'Binh Phu park', '10.74477543934487', '106.63052476371975')");
        db.execSQL("insert into Event VALUES(1, 2, 'Collecting rubbish', 'Collecting rubbish make a party','24/4/2021', '19:00', 'Hoang Van Thu park', '10.802133844324004', '106.66449802600644')");
        db.execSQL("insert into Event VALUES(2, 0, 'Clean plastic from ocean', 'Diving in an ocean to collect plastic rubbish and also take a picture','24/4/2021','20:00', 'Can Gio shore', '10.389167165634552', '106.92593867009207')");
        db.execSQL("insert into Event VALUES(3, 1, 'Saving animal', 'Remove parasite from sea creature such as lobsters and crabs','25/4/2021','21:00', 'Vung Tau shore', '10.375228824726978', '107.12753146525627')");
        db.execSQL("insert into Event VALUES(4, 3, 'Saving food crop from boars', 'Hunting boars with barreta prevent them from harm our food crops','26/4/2021','10:00', 'Lam Ha forest', '11.821354257237576', '108.2855351197866')");
        db.execSQL("insert into Event VALUES(5, 4, 'Blood Donation', 'Blood donation at Nguyen Van Cu and get reward', '24/4/2021', '13:00', 'Truong dai hoc Khoa Hoc Tu Nhien', '10.76302893338683', '106.68213951066296')");
        db.execSQL("insert into Event VALUES(6, 1, 'Cooking delivery for ethnic', 'Explore traditional food and bringing modern dishes to ethnic', '26/4/2021', '14:00', 'Muong Hoa valley', '22.33029424290214', '103.84840611618972')");
        db.execSQL("insert into Event VALUES(7, 0, 'Teaching ethnic', 'Open teaching classes for ethnic for free', '30/4/2021', '6:00', 'Muong Hoa valley', '22.33029424290214', '103.84840611618972')");
        db.execSQL("insert into Event VALUES(8, 0, 'Home for Orphans', 'Assemble Orphans and give them a home', 'Hoa Hung market', '1/5/2021', '7:00', '10.784376775425633', '106.67143904016929')");

        db.execSQL("insert into Participant VALUES(0, 0)");
        db.execSQL("insert into Participant VALUES(0, 1)");
        db.execSQL("insert into Participant VALUES(0, 2)");
        db.execSQL("insert into Participant VALUES(1, 2)");
        db.execSQL("insert into Participant VALUES(1, 1)");
        db.execSQL("insert into Participant VALUES(1, 3)");
        db.execSQL("insert into Participant VALUES(2, 0)");
        db.execSQL("insert into Participant VALUES(2, 3)");
        db.execSQL("insert into Participant VALUES(2, 4)");
        db.execSQL("insert into Participant VALUES(3, 1)");
        db.execSQL("insert into Participant VALUES(3, 3)");
        db.execSQL("insert into Participant VALUES(3, 2)");
        db.execSQL("insert into Participant VALUES(4, 3)");
        db.execSQL("insert into Participant VALUES(4, 4)");
        db.execSQL("insert into Participant VALUES(4, 4)");
        db.execSQL("insert into Participant VALUES(5, 0)");
        db.execSQL("insert into Participant VALUES(5, 1)");
        db.execSQL("insert into Participant VALUES(5, 2)");

        db.execSQL("insert into Comment VALUES(0, 0, 'Welcome everyone', '16-4-2021', '15:30:00')");
        db.execSQL("insert into Comment VALUES(0, 1, 'I will give this a donation, check your bank account for me', '17-4-2021', '16:20:00')");
        db.execSQL("insert into Comment VALUES(0, 0, 'Thank you very much', '18-04-2021', '19:00:02')");

        db.execSQL("insert into Topic VALUES(0, 'Environment')");
        db.execSQL("insert into Topic VALUES(1, 'Animal')");
        db.execSQL("insert into Topic VALUES(2, 'Charity')");
        db.execSQL("insert into Topic VALUES(3, 'Blood Donation')");
        db.execSQL("insert into Topic VALUES(4, 'Culture')");
        db.execSQL("insert into Topic VALUES(5, 'Education')");
        db.execSQL("insert into Topic VALUES(6, 'Children')");

        db.execSQL("insert into Interest VALUES(0, 1)");
        db.execSQL("insert into Interest VALUES(1, 5)");
        db.execSQL("insert into Interest VALUES(2, 0)");
        db.execSQL("insert into Interest VALUES(3, 1)");
        db.execSQL("insert into Interest VALUES(4, 1)");
        db.execSQL("insert into Interest VALUES(4, 0)");
        db.execSQL("insert into Interest VALUES(5, 3)");
        db.execSQL("insert into Interest VALUES(6, 4)");
        db.execSQL("insert into Interest VALUES(6, 6)");
        db.execSQL("insert into Interest VALUES(7, 4)");
        db.execSQL("insert into Interest VALUES(7, 5)");
        db.execSQL("insert into Interest VALUES(7, 6)");
        db.execSQL("insert into Interest VALUES(8, 6)");
        db.execSQL("insert into Interest VALUES(8, 2)");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, Splash_time_out);
    }
}
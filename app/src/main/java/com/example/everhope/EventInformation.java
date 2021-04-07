package com.example.everhope;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventInformation extends Activity {
    FloatingActionButton btnSeeMore;
    ExtendedFloatingActionButton btnRate, btnAddComment, btnReport, btnMap, btnJoin;
    Animation rotateClose, rotateOpen, fromBottom, toBottom;
    boolean clicked = false;

    ViewGroup icons_view;
    int [] icons = {R.drawable.image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_info);
        icons_view = (ViewGroup) findViewById(R.id.eventParticipant);

        int maxMember = 10;
        for (int i = 0 ; i < maxMember ; i++){
            final View singleIcon = getLayoutInflater().inflate(R.layout.participant_icon, null);
            singleIcon.setId(i);
            ImageView personalIcon = (ImageView) singleIcon.findViewById(R.id.personal_icon);
            personalIcon.setImageResource(icons[0]);
            icons_view.addView(singleIcon);
            singleIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventInformation.this, userProfile.class);
                    startActivity(intent);
                }
            });
        }

        btnSeeMore = (FloatingActionButton) findViewById(R.id.btnSeeMore);
        btnAddComment = (ExtendedFloatingActionButton) findViewById(R.id.btnAddComment);
        btnMap = (ExtendedFloatingActionButton) findViewById(R.id.btnMap);
        btnRate = (ExtendedFloatingActionButton) findViewById(R.id.btnRate);
        btnReport = (ExtendedFloatingActionButton) findViewById(R.id.btnReport);
        btnJoin = (ExtendedFloatingActionButton) findViewById(R.id.btnJoin);


        rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_anim);
        fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });


        btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    btnRate.setVisibility(View.INVISIBLE);
                    btnAddComment.setVisibility(View.INVISIBLE);
                    btnReport.setVisibility(View.INVISIBLE);
                    btnMap.setVisibility(View.INVISIBLE);
                    btnJoin.setVisibility(View.INVISIBLE);
                } else {
                    btnRate.setVisibility(View.VISIBLE);
                    btnAddComment.setVisibility(View.VISIBLE);
                    btnReport.setVisibility(View.VISIBLE);
                    btnMap.setVisibility(View.VISIBLE);
                    btnJoin.setVisibility(View.VISIBLE);
                }
                if (clicked) {
                    btnRate.startAnimation(toBottom);
                    btnAddComment.startAnimation(toBottom);
                    btnReport.startAnimation(toBottom);
                    btnMap.startAnimation(toBottom);
                    btnJoin.startAnimation(toBottom);
                    btnSeeMore.startAnimation(rotateClose);
                } else {
                    btnRate.startAnimation(fromBottom);
                    btnAddComment.startAnimation(fromBottom);
                    btnReport.startAnimation(fromBottom);
                    btnMap.startAnimation(fromBottom);
                    btnJoin.startAnimation(fromBottom);
                    btnSeeMore.startAnimation(rotateOpen);
                }
                clicked = !clicked;
            }
        });
    }
}

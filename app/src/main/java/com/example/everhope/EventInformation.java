package com.example.everhope;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class EventInformation extends Activity {
    FloatingActionButton btnSeeMore;
    ExtendedFloatingActionButton btnRate, btnAddComment, btnReport, btnMap, btnJoin;
    Animation rotateClose, rotateOpen, fromBottom, toBottom;
    boolean clicked = false;
    ViewGroup icons_view;
    int[] icons = {R.drawable.image};

    FloatingActionButton btnSetting;
    EditText editName, editDescription, editDatetime, editLocation, editHostPhone, editHostName, editField;
    TextView btnDeleteEvent, btnChange;
    FloatingActionButton btnCloseEventEdit;
    TextView eventName, eventDescription, eventInterest, eventOrganizer, eventDatetime, eventContact, eventLocation;
    Button btnLocation, btnField, btnDatetime;


    int mYear, mMonth, mDay, mHour, mMinute;
    String dt="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_info);
        icons_view = (ViewGroup) findViewById(R.id.eventParticipant);
        int maxMember = 10;
        for (int i = 0; i < maxMember; i++) {
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

        eventName = (TextView) findViewById(R.id.eventName);
        eventDescription = (TextView) findViewById(R.id.eventDescription);
        eventInterest = (TextView) findViewById(R.id.eventInterest);
        eventOrganizer = (TextView) findViewById(R.id.eventOrganizer);
        eventDatetime = (TextView) findViewById(R.id.eventDatetime);
        eventContact = (TextView) findViewById(R.id.eventContact);
        eventLocation = (TextView) findViewById(R.id.eventLocation);

        btnSetting = (FloatingActionButton) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
    }

    public void showEditDialog() {
        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.event_info_edit, null);

        editField = (EditText) view.findViewById(R.id.editField);
        editName = (EditText) view.findViewById(R.id.editName);
        editDescription = (EditText) view.findViewById(R.id.editDescription);
        editDatetime = (EditText) view.findViewById(R.id.editDatetime);
        editLocation = (EditText) view.findViewById(R.id.editLocation);
        editHostPhone = (EditText) view.findViewById(R.id.editHostPhone);
        editHostName = (EditText) view.findViewById(R.id.editHostName);
        btnLocation =(Button)view.findViewById(R.id.btnEditLocation);
        btnField =(Button)view.findViewById(R.id.btnEditField);
        btnDatetime =(Button)view.findViewById(R.id.btnEditDatetime);



        editField.setText(eventInterest.getText().toString());
        editName.setText(eventName.getText().toString());
        editDescription.setText(eventDescription.getText().toString());
        editDatetime.setText(eventDatetime.getText().toString());
        editLocation.setText(eventLocation.getText().toString());
        editHostPhone.setText(eventContact.getText().toString());
        editHostName.setText(eventOrganizer.getText().toString());

        btnCloseEventEdit = (FloatingActionButton) view.findViewById(R.id.btnCloseEventEdit);
        btnDeleteEvent = (TextView) view.findViewById(R.id.btnDeleteEvent);
        btnChange = (TextView) view.findViewById(R.id.btnChange);

        alert.setView(view);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventInterest.setText(editField.getText().toString());
                eventName.setText(editName.getText().toString());
                eventDescription.setText(editDescription.getText().toString());
                eventDatetime.setText(editDatetime.getText().toString());
                eventLocation.setText(editLocation.getText().toString());
                eventOrganizer.setText(editHostName.getText().toString());
                eventContact.setText(editHostPhone.getText().toString());
                ///////////////// luu vao database
                dialog.dismiss();
            }
        });

        btnCloseEventEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(EventInformation.this);
                alertDialogBuilder.setMessage("Are you sure to delete this? This action cannot be undone.");
                alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
                alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        /////////////////(?) xoa event khoi database
                        arg0.dismiss();
                        dialog.dismiss();
                    }
                });
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                ////////// ......
            }
        });

        btnField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EventInformation.this);
                String[] interestsArray = new String[]{"Children", "Environment", "Culture", "Education"};
                boolean[] checkedInterests = new boolean[]{false, false, false, false};

                String[] tempToken = editField.getText().toString().split("   ");
                for (int idx = 0; idx < tempToken.length; idx++) {
                    for (int idy = 0; idy < interestsArray.length; idy++) {
                        if (tempToken[idx].compareTo(interestsArray[idy]) == 0) {
                            checkedInterests[idy] = true;
                            break;
                        }
                    }
                }

                final List<String> interestsList = Arrays.asList(interestsArray);
                builder.setTitle("Select this event's interests/fields");
                builder.setMultiChoiceItems(interestsArray, checkedInterests, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        checkedInterests[which] = isChecked;
                    }
                });

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String intst = "";
                        int i = 0;
                        for (; i < checkedInterests.length; i++) {
                            boolean checked = checkedInterests[i];
                            if (checked) {
                                intst += interestsList.get(i) + "   ";
                            }
                        }
                        editField.setText(intst);
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
                builder.create().show();
            }
        });
//

        btnDatetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EventInformation.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dt+=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(EventInformation.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {

                                                dt+="   " + hourOfDay + ":" + minute;
                                                editDatetime.setText(dt);
                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                dt="";
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //................
            }
        });
    }
}
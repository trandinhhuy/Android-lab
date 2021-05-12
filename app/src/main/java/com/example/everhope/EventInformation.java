package com.example.everhope;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.everhope.supportClass.UpdateFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class EventInformation extends Activity {
    FloatingActionButton btnSeeMore;
    ExtendedFloatingActionButton btnAddComment, btnReport, btnMap, btnJoin;
    Animation rotateClose, rotateOpen, fromBottom, toBottom;
    boolean clicked = false;
    ViewGroup icons_view;
    String location;
    FloatingActionButton btnSetting;
    EditText editName, editDescription, editDatetime, editLocation, editHostPhone, editHostName, editField;
    TextView btnDeleteEvent, btnChange, btnDial;
    FloatingActionButton btnCloseEventEdit;
    TextView eventName, eventDescription, eventInterest, eventOrganizer, eventDatetime, eventContact, eventLocation;
    Button btnLocation, btnField, btnDatetime;


    int mYear, mMonth, mDay, mHour, mMinute;
    String dt="";
    private String EventID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_event_information);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String userID = MenuActivity.getMyLoginPref(getApplicationContext());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        location = bundle.getString("title", "");

        EventID = bundle.getString("EventID", "");

        icons_view = (ViewGroup) findViewById(R.id.eventParticipant);

        FirebaseDatabase participantFirebase = FirebaseDatabase.getInstance();
        DatabaseReference participantRef = participantFirebase.getReference().child("EventParticipant/Event" + EventID);
        participantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int countID = 0;
                icons_view = (ViewGroup) findViewById(R.id.eventParticipant);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userParticipant = dataSnapshot.getKey();
                    final View singleIcon = getLayoutInflater().inflate(R.layout.participant_icon, null);
                    singleIcon.setId(countID);
                    countID ++;
                    setImage("Avatar/User" + userParticipant, R.id.personal_icon, singleIcon);
                    singleIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent1 = new Intent(getApplicationContext(), userProfile.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("UserID", userParticipant);
                            intent1.putExtras(bundle1);
                            startActivity(intent1);
                        }
                    });
                    icons_view.addView(singleIcon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSeeMore = (FloatingActionButton) findViewById(R.id.btnSeeMore);
        btnAddComment = (ExtendedFloatingActionButton) findViewById(R.id.btnAddComment);
        btnMap = (ExtendedFloatingActionButton) findViewById(R.id.btnMap);
        btnReport = (ExtendedFloatingActionButton) findViewById(R.id.btnReport);
        btnJoin = (ExtendedFloatingActionButton) findViewById(R.id.btnJoin);
        btnDial = (TextView)findViewById(R.id.btn_dial);


        rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_anim);
        fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);


        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EventID", EventID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        String finalLocation1 = location;
        btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    btnAddComment.setVisibility(View.INVISIBLE);
                    btnReport.setVisibility(View.INVISIBLE);
                    btnMap.setVisibility(View.INVISIBLE);
                    btnJoin.setVisibility(View.INVISIBLE);
                } else {
                    btnAddComment.setVisibility(View.VISIBLE);
                    btnReport.setVisibility(View.VISIBLE);
                    btnMap.setVisibility(View.VISIBLE);

                    FirebaseDatabase checkParticipant = FirebaseDatabase.getInstance();
                    DatabaseReference checkParticipantRef = checkParticipant.getReference().child("EventParticipant/Event" + EventID);
                    String finalLocation = finalLocation1;
                    checkParticipantRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot userParticipant : snapshot.getChildren()){
                                if (userID.compareTo(String.valueOf(userParticipant.getKey())) == 0) {
                                    btnJoin.setText("Unjoin");
                                    if (String.valueOf(userParticipant.getValue()).compareTo("1") == 0){
                                        btnJoin.setEnabled(false);
                                    }
                                    btnJoin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent1 = new Intent(getApplicationContext(), EventInformation.class);
                                            Bundle bundle1 = new Bundle();
                                            bundle1.putString("title", finalLocation);
                                            bundle1.putString("EventID", EventID);
                                            intent1.putExtras(bundle1);
                                            UpdateFirebase.removeData("EventParticipant/Event" + EventID + "/" + userID);
                                            startActivity(intent1);
                                            finish();
                                        }
                                    });
                                    return;
                                }
                            }
                            btnJoin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UpdateFirebase.updateData("EventParticipant/Event" + EventID + "/" + userID, "0");
                                    Intent intent2 = new Intent(getApplicationContext(), EventInformation.class);
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putString("title", finalLocation);
                                    bundle2.putString("EventID", EventID);
                                    intent2.putExtras(bundle2);
                                    startActivity(intent2);
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                if (clicked) {
                    btnAddComment.startAnimation(toBottom);
                    btnReport.startAnimation(toBottom);
                    btnMap.startAnimation(toBottom);
                    btnJoin.startAnimation(toBottom);
                    btnSeeMore.startAnimation(rotateClose);
                } else {
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
        eventLocation.setText(location);

        btnSetting = (FloatingActionButton) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference().child("Event/" + EventID);
        String finalLocation = location;
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
// render data
                eventName.setText(String.valueOf(snapshot.child("Name").getValue()));
                eventDescription.setText(String.valueOf(snapshot.child("Description").getValue()));
                eventInterest.setText(String.valueOf(snapshot.child("Interest").getValue()));
                String organizerID = String.valueOf(snapshot.child("Organizer").getValue());

                if (organizerID.compareTo(userID) == 0){
                    btnJoin.setVisibility(View.INVISIBLE);
                    btnSetting.setVisibility(View.VISIBLE);
                    btnSetting.setEnabled(true);
                }
                else {

                    btnSetting.setVisibility(View.INVISIBLE);
                }
                FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
                DatabaseReference userRef = firebaseDatabase1.getReference().child("User/" + organizerID);
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        eventOrganizer.setText(String.valueOf(snapshot.child("Name").getValue()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                StorageReference eventImageStore = firebaseStorage.getReference().child("Event/Event" + EventID);
                eventImageStore.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference getImage : listResult.getItems()){
                            try {
                                final File eventImageFile = File.createTempFile("eventImage", "jpg");
                                getImage.getFile(eventImageFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(eventImageFile.getAbsolutePath());
                                        ImageView eventImage = (ImageView) findViewById(R.id.event_image);
                                        eventImage.setImageBitmap(bitmap);
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

                eventDatetime.setText(String.valueOf(snapshot.child("Datetime").getValue()));
                eventLocation.setText(String.valueOf(snapshot.child("Location").getValue()));
                eventContact.setText(String.valueOf(snapshot.child("Phone").getValue()));
                btnMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("action", "view");
                        bundle.putString("Lat", String.valueOf(snapshot.child("Lat").getValue()));
                        bundle.putString("Long", String.valueOf(snapshot.child("Long").getValue()));
                        bundle.putString("Position", String.valueOf(snapshot.child("Location").getValue()));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
// render data
                if (!finalLocation.isEmpty()){
                    showEditDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(getApplicationContext(), ReportEvent.class);
                Bundle reportBundle = new Bundle();
                reportBundle.putString("EventID", EventID);
                reportBundle.putString("EventName", String.valueOf(eventName.getText()));
                reportIntent.putExtras(reportBundle);
                startActivity(reportIntent);
            }
        });

        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = eventContact.getText().toString();
                if (phoneNo.length()>0){
                    Uri uri = Uri.parse("tel:" + phoneNo);
                    Intent i = new Intent(Intent.ACTION_DIAL, uri);
                    try{
                        startActivity(i);
                    }
                    catch (SecurityException s){
                        Toast.makeText(EventInformation.this,"Try again later.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }



    public void showEditDialog() {
        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.event_info_edit, null);

        editField = (EditText) view.findViewById(R.id.new_task_interest);
        editName = (EditText) view.findViewById(R.id.new_task_name);
        editDescription = (EditText) view.findViewById(R.id.new_task_description);
        editDatetime = (EditText) view.findViewById(R.id.new_task_date);
        editLocation = (EditText) view.findViewById(R.id.new_task_location);
        editHostPhone = (EditText) view.findViewById(R.id.new_task_phone_number);
        editHostName = (EditText) view.findViewById(R.id.new_task_host_name);
        btnLocation =(Button)view.findViewById(R.id.new_task_location_select);
        btnField =(Button)view.findViewById(R.id.new_task_interest_field);
        btnDatetime =(Button)view.findViewById(R.id.new_task_date_time);



        editField.setText(eventInterest.getText().toString());
        editName.setText(eventName.getText().toString());
        editDescription.setText(eventDescription.getText().toString());
        editDatetime.setText(eventDatetime.getText().toString());
        if (location.isEmpty())
            editLocation.setText(eventLocation.getText().toString());
        else
            editLocation.setText(location);
        editHostPhone.setText(eventContact.getText().toString());
        if (location.isEmpty())
            editHostName.setText(eventOrganizer.getText().toString());
        else{
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("User/" + MenuActivity.getMyLoginPref(getApplicationContext()));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    editHostName.setText(String.valueOf(snapshot.child("Name").getValue()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        editHostName.setEnabled(false);

        btnCloseEventEdit = (FloatingActionButton) view.findViewById(R.id.btnCloseEventEdit);
        btnDeleteEvent = (TextView) view.findViewById(R.id.new_task_cancel);
        btnChange = (TextView) view.findViewById(R.id.new_task_add_event);

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
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                String EventID = bundle.getString("EventID", "");
                String lat = bundle.getString("latitude", "");
                String lng = bundle.getString("longitude", "");
                UpdateFirebase.updateData("Event/" + EventID + "/Name", editName.getText().toString());
                UpdateFirebase.updateData("Event/" + EventID + "/Interest", editField.getText().toString());
                UpdateFirebase.updateData("Event/" + EventID + "/Description", editDescription.getText().toString());
                UpdateFirebase.updateData("Event/" + EventID + "/Datetime", editDatetime.getText().toString());
                UpdateFirebase.updateData("Event/" + EventID + "/Location", editLocation.getText().toString());
                UpdateFirebase.updateData("Event/" + EventID + "/Phone", editHostPhone.getText().toString());
                if (!lat.isEmpty() && !lng.isEmpty()){
                    UpdateFirebase.updateData("Event/" + EventID + "/Lat", lat);
                    UpdateFirebase.updateData("Event/" + EventID + "/Long", lng);
                }
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
                        UpdateFirebase.removeData("Event/" + EventID);
                        finish();
                        //todo
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
                String[] interestsArray = new String[]{"Environment", "Children", "Culture", "Education", "Animals", "Homeless", "Health", "Broadcasting", "Sports", "People"};
                boolean[] checkedInterests = new boolean[]{false, false, false, false,false, false, false, false,false, false};

                String[] tempToken = editField.getText().toString().split(", ");
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
                        for (int i = 0; i < checkedInterests.length; i++) {
                            boolean checked = checkedInterests[i];
                            if (checked) {
                                intst += (intst.length()>0 ?", ":"")+interestsList.get(i);
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
                                dt+=(dayOfMonth<10?"0":"")+dayOfMonth + "/" + (monthOfYear<9?"0":"")+(monthOfYear + 1) + "/" + year;

                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(EventInformation.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {

                                                dt+=" " + (hourOfDay<10?"0":"")+hourOfDay + ":" + (minute<10?"0":"")+minute;
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
                Bundle bundle = new Bundle();
                bundle.putString("action", "edit");
                bundle.putString("EventID", EventID);
                Intent intent = new Intent(EventInformation.this, MapsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

        });

    }
    public static void setImage(String imageSrc, Integer layout, View root){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference imageRef = firebaseStorage.getReference().child(imageSrc);
        imageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()){
                    try {
                        final File file = File.createTempFile("image", "jpg");
                        item.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                ImageView imageLayout = (ImageView) root.findViewById(layout);
                                imageLayout.setImageBitmap(bitmap);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void checkOwner(){
        String userID = MenuActivity.getMyLoginPref(getApplicationContext());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String EventID = bundle.getString("EventID", "");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Event/" + EventID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String owner = String.valueOf(snapshot.child("Organizer").getValue());
                if (owner.compareTo(userID) == 0){
                    btnJoin = (ExtendedFloatingActionButton) findViewById(R.id.btnJoin);
                    btnJoin.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.everhope;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.everhope.supportClass.UpdateFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


public class AddEventActivity extends AppCompatActivity {
    Button new_task_date_time,new_task_location_select,new_task_interest_field;
    TextView new_task_add_event;
    EditText new_task_name,new_task_description,new_task_date,new_task_location,new_task_interest,new_task_phone_number,new_task_host_name;

    String Lat="", Long="";
    final String uuid = UUID.randomUUID().toString().replace("-", "");

    int mYear, mMonth, mDay, mHour, mMinute; String dt="";
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_task);
        String userID = MenuActivity.getMyLoginPref(getApplicationContext());
        getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.new_task_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new_task_host_name = (EditText) findViewById(R.id.new_task_host_name);
        new_task_date = (EditText) findViewById(R.id.new_task_date);
        new_task_description = (EditText)findViewById(R.id.new_task_description);
        new_task_interest = (EditText) findViewById(R.id.new_task_interest);
        new_task_name = (EditText) findViewById(R.id.new_task_name);
        new_task_phone_number = (EditText) findViewById(R.id.new_task_phone_number);
        new_task_location = (EditText) findViewById(R.id.new_task_location);
        new_task_add_event = (TextView) findViewById(R.id.new_task_add_event);
        new_task_location_select = (Button) findViewById(R.id.new_task_location_select);
        new_task_interest_field = (Button) findViewById(R.id.new_task_interest_field);
        new_task_date_time = (Button)findViewById(R.id.new_task_date_time);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reportRef = firebaseDatabase.getReference().child("User/" + userID);
        reportRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new_task_host_name.setText(String.valueOf(snapshot.child("Name").getValue()));
                new_task_host_name.setEnabled(false);
                new_task_phone_number.setText(String.valueOf(snapshot.child("Phone").getValue()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        new_task_location_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
                Lat = "";
                Long = "";
                new_task_location.setText("Selected");
            }
        });

        new_task_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dt+=(dayOfMonth<10?"0":"")+dayOfMonth + "/" + (monthOfYear<9?"0":"")+(monthOfYear + 1) + "/" + year;


                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                dt+=" " + (hourOfDay<10?"0":"")+hourOfDay + ":" + (minute<10?"0":"")+minute;
                                                new_task_date.setText(dt);
                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                dt="";
            }
        });
        new_task_interest_field.setOnClickListener(new View.OnClickListener() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);
            @Override
            public void onClick(View v) {
                String[] interestsArray = new String[]{"Environment", "Children", "Culture", "Education", "Animals", "Homeless", "Health", "Broadcasting", "Sports", "People"};
                boolean[] checkedInterests = new boolean[]{false, false, false, false,false, false, false, false,false,false};

                String[] tempToken = new_task_interest.getText().toString().split(", ");
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
                        new_task_interest.setText(intst);
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

        new_task_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task_name,task_description,task_date,task_location,task_interest,task_phone_number;
                task_date = new_task_date.getText().toString();
                task_location = new_task_location.getText().toString();
                task_description = new_task_description.getText().toString();
                task_name = new_task_name.getText().toString();
                task_interest = new_task_interest.getText().toString();
                task_phone_number = new_task_phone_number.getText().toString();
                if (task_date.length()<1 || task_location.length()<1 || task_description.length()<1
                        || task_name.length()<1 || task_interest.length()<1 || task_phone_number.length()<1){
                    showDialog("Please fill all the required fields and try again!");
                    return;
                }
                EventObj n = new EventObj("0",task_date,task_description,task_interest,Lat,Long,task_location,task_name,userID,task_phone_number);
                UpdateFirebase.updateNewEvent("Event/"+uuid,n);
                UpdateFirebase.updateParticipant("EventParticipant/"+uuid,userID,"1");
                UpdateFirebase.updateComment("EventComment/"+uuid,new CommentObj(userID, "Welcome! Join us!"));
            }
        });

    }

    public void showDialog(String dialogMessage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

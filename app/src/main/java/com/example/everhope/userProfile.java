package com.example.everhope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class userProfile extends Activity {
    String curPass = "abc123";
    Button btn_update,  btn_changepass, btn_updatepass;
    ImageButton btn_edit, btn_rp_user;
    EditText edit_name, edit_des, edit_dob, edit_interests, edit_currentpass, edit_newpass, edit_confirmpass, edit_phone, edit_gender;
    TextView username, dob, des, interests, gender, phone;
    Calendar c;
    DatePickerDialog dpd;
    FloatingActionButton btn_exit, btn_exitpass;
    int checkedItem = 0;
    String dt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        username = (TextView) findViewById(R.id.username);
        des = (TextView) findViewById(R.id.des);
        dob = (TextView) findViewById(R.id.dob);
        phone = (TextView) findViewById(R.id.phone);
        gender = (TextView) findViewById(R.id.gender);
        interests = (TextView) findViewById(R.id.interests);
        btn_edit = (ImageButton) findViewById(R.id.btn_edit);
        btn_rp_user = (ImageButton)findViewById(R.id.btn_rp_user) ;

        Toolbar toolbar = findViewById(R.id.user_profile_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String userId = bundle.getString("UserID", "");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("User/" + userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(String.valueOf(snapshot.child("Name").getValue()));
                des.setText(String.valueOf(snapshot.child("Description").getValue()));
                dob.setText(String.valueOf(snapshot.child("Dob").getValue()));
                interests.setText(String.valueOf(snapshot.child("Interest").getValue()));
                phone.setText(String.valueOf(snapshot.child("Phone").getValue()));
                gender.setText(String.valueOf(snapshot.child("Gender").getValue()));
                EventInformation.setImage("Avatar/User" + userId, R.id.user_profile_avatar, getWindow().getDecorView());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (MenuActivity.getMyLoginPref(getApplicationContext()).compareTo(userId) != 0){
            btn_edit.setVisibility(View.INVISIBLE);
        }
        if (MenuActivity.getMyLoginPref(getApplicationContext()).compareTo(userId) == 0){
            btn_rp_user.setVisibility(View.INVISIBLE);
        }
        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDialog();
            }
        });
        btn_rp_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent reportIntent = new Intent(getApplicationContext(), ReportUser.class);
                Bundle reportBundle = new Bundle();
                reportBundle.putString("ReportUserID", userId);
                reportIntent.putExtras(reportBundle);
                startActivity(reportIntent);
            }
        });


    }

    private void showDialog() {
        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(this, R.style.MaterialThemeDialog);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.up_edit, null);

        edit_name = (EditText) view.findViewById(R.id.edit_name);
        edit_name.setText(username.getText().toString());
        edit_des = (EditText) view.findViewById(R.id.edit_des);
        edit_des.setText(des.getText().toString());
        edit_dob = (EditText) view.findViewById(R.id.edit_dob);
        edit_dob.setText(dob.getText().toString());
        edit_interests = (EditText) view.findViewById(R.id.edit_interests);
        edit_interests.setText(interests.getText().toString());
        edit_gender = (EditText) view.findViewById(R.id.edit_gender);
        edit_gender.setText(gender.getText().toString());
        edit_phone = (EditText) view.findViewById(R.id.edit_phone);
        edit_phone.setText(phone.getText().toString());
        btn_update = (Button) view.findViewById(R.id.btn_update);
        btn_exit = (FloatingActionButton) view.findViewById(R.id.btn_exit);
        btn_changepass = (Button) view.findViewById(R.id.btn_changepass);

        alert.setView(view);
        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.show();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText(edit_name.getText().toString());
                des.setText(edit_des.getText().toString());
                dob.setText(edit_dob.getText().toString());
                interests.setText(edit_interests.getText().toString());
                gender.setText(edit_gender.getText().toString());
                phone.setText(edit_phone.getText().toString());
                String ID = MenuActivity.getMyLoginPref(getApplicationContext());
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                String path = "User/"+ID;
                firebaseDatabase.getReference(path+"/Description").setValue(edit_des.getText().toString());
                firebaseDatabase.getReference(path+"/Dob").setValue(edit_dob.getText().toString());
                firebaseDatabase.getReference(path+"/Interest").setValue(edit_interests.getText().toString());
                firebaseDatabase.getReference(path+"/Gender").setValue(edit_gender.getText().toString());
                firebaseDatabase.getReference(path+"/Phone").setValue(edit_phone.getText().toString());
                firebaseDatabase.getReference(path+"/Name").setValue(edit_name.getText().toString());


                dialog.dismiss();

            }
        });

        edit_dob.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(userProfile.this,R.style.alert, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dt=(mDay<10?"0":"")+mDay + "/" + (mMonth<9?"0":"")+(mMonth + 1) + "/" + mYear;
                        edit_dob.setText(dt);
                    }
                }, day,month, year);
                dpd.show();
            }
        });

        edit_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(userProfile.this);
                alertDialog.setTitle("Choose Gender");
                String[] items = {"Male","Female","Others"};
                String g = edit_gender.getText().toString();
                if(g.compareTo("Male")==0){
                    checkedItem=0;
                }
                else if(g.compareTo("Female")==0){
                    checkedItem=1;
                }
                else checkedItem=2;
                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem = which;
                    }
                });

                alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit_gender.setText(items[checkedItem]);
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        edit_interests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(userProfile.this);
                String[] interestsArray = new String[]{"Environment", "Children", "Culture", "Education", "Animals", "Homeless", "Health", "Broadcasting", "Sports", "People"};
                boolean[] checkedInterests = new boolean[]{false, false, false, false,false, false, false, false,false, false};

                String[] tempToken = edit_interests.getText().toString().split(", ");
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
                        edit_interests.setText(intst);
                    }
                });


                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btn_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });


        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showDialog1() {
        AlertDialog.Builder alert1;

        alert1 = new AlertDialog.Builder(this, R.style.MaterialThemeDialog);


        LayoutInflater inflater1 = getLayoutInflater();

        View view1 =  inflater1.inflate(R.layout.up_changepass,null);


        edit_currentpass = (EditText) view1.findViewById(R.id.edit_currentpass);
        edit_newpass = (EditText) view1.findViewById(R.id.edit_newpass);
        edit_confirmpass = (EditText) view1.findViewById(R.id.edit_confirmpass);
        btn_updatepass = (Button) view1.findViewById(R.id.btn_updatepass);
        btn_exitpass = (FloatingActionButton) view1.findViewById(R.id.btn_exitpass);

        alert1.setView(view1);
        alert1.setCancelable(false);

        AlertDialog dialog1 = alert1.create();
        dialog1.show();
        btn_updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curPass.equals(edit_currentpass.getText().toString()) && edit_newpass.getText().toString().equals(edit_confirmpass.getText().toString())){
                    Toast.makeText(userProfile.this,"Change password successfully!",Toast.LENGTH_SHORT).show();
                    dialog1.dismiss();

                }
                else {
                    Toast.makeText(userProfile.this,"Error! Please re-enter",Toast.LENGTH_SHORT).show();
                }


            }
        });




        btn_exitpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


    }

}
package com.example.registerform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

public class registerform extends AppCompatActivity {
    private int selectedYear, selectedMonth, selectedDate;
    public static final String EXTRA_username = "com.example.registerform.username";
    public static final String EXTRA_password = "com.example.registerform.password";
    public static final String EXTRA_birthday = "com.example.registerform.birthday";
    public static final String EXTRA_gender = "com.example.registerform.gender";
    public static final String EXTRA_hobbies = "com.example.registerform.hobbies";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getBooleanExtra("EXIT", false) == true){
            finish();
        }
        Button selectBtn = (Button) findViewById(R.id.selectBtn);
        Button resetBtn = (Button) findViewById(R.id.resetBtn);
        Button signUpBtn = (Button) findViewById(R.id.signupBtn);

        selectBtn.setBackgroundColor(R.color.darkgray);
        resetBtn.setBackgroundColor(R.color.darkgray);
        signUpBtn.setBackgroundColor(R.color.darkgray);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate();
            }
        });
        final Calendar c = Calendar.getInstance();
        this.selectedYear = c.get(Calendar.YEAR);
        this.selectedMonth = c.get(Calendar.MONTH);
        this.selectedDate = c.get(Calendar.DAY_OF_MONTH);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.usernameTxt);
                EditText password = (EditText) findViewById(R.id.passwordTxt);
                EditText retype = (EditText) findViewById(R.id.retypeTxt);
                EditText birthday = (EditText) findViewById(R.id.birtthdayEdit);

                RadioGroup radioGroup = (RadioGroup) findViewById((R.id.group));

                CheckBox tennis = (CheckBox) findViewById(R.id.tennixBox);
                CheckBox futbal = (CheckBox) findViewById(R.id.futbalBox);
                CheckBox other = (CheckBox) findViewById(R.id.otherBox);

                username.setText("");
                password.setText("");
                retype.setText("");
                birthday.setText("");
                radioGroup.clearCheck();

                tennis.setChecked(false);
                futbal.setChecked(false);
                other.setChecked(false);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText checkValid = (EditText) findViewById(R.id.usernameTxt);
                if (checkValid.getText().toString().isEmpty()){
                    openDialog("Username cannot empty");
                    return;
                }
                checkValid = (EditText) findViewById(R.id.passwordTxt);
                if (checkValid.getText().toString().isEmpty()){
                    openDialog("Password cannot empty");
                    return;
                }
                EditText retype = (EditText) findViewById(R.id.retypeTxt);
                if (checkValid.getText().toString().compareTo(retype.getText().toString()) != 0){
                    openDialog("Password and retype does not match");
                    return;
                }
                checkValid = (EditText) findViewById(R.id.birtthdayEdit);
                String birthday = checkValid.getText().toString();
                DateValidator validator = new DateValidatorWithFormat("dd/MM/yyyy");
                if (!validator.isValid(birthday)){
                    openDialog("Birthday is not valid");
                    return;
                }
                startActivity2();
            }
        });
    }
    public void startActivity2(){
        EditText username = (EditText) findViewById(R.id.usernameTxt);
        String usernameDt = username.getText().toString();

        EditText password = (EditText) findViewById(R.id.passwordTxt);
        String passwordDt = password.getText().toString();

        EditText birthday = (EditText) findViewById(R.id.birtthdayEdit);
        String birthdayDt = birthday.getText().toString();

        String genderDt = "Unknown";
        RadioButton maleChecked = (RadioButton) findViewById(R.id.maleCk);
        RadioButton femaleChecked = (RadioButton) findViewById(R.id.femaleCk);

        if (maleChecked.isChecked()){
            genderDt = "Male";
        }
        if (femaleChecked.isChecked()){
            genderDt = "Female";
        }
        String hobbiesDt = "";

        CheckBox tennis = (CheckBox) findViewById(R.id.tennixBox);
        if (tennis.isChecked()) hobbiesDt += "Tennis";

        CheckBox futbal = (CheckBox) findViewById(R.id.futbalBox);
        if (futbal.isChecked()){
            if (hobbiesDt.isEmpty()){
                hobbiesDt = "Futbal";
            }
            else{
                hobbiesDt += ", Futbal";
            }
        }
        CheckBox other = (CheckBox) findViewById(R.id.otherBox);
        if (other.isChecked()){
            if (hobbiesDt.isEmpty()){
                hobbiesDt = "Others";
            }
            else{
                hobbiesDt += " and others";
            }
        }

        Intent intent = new Intent(this, resultform.class);
        intent.putExtra(EXTRA_username, usernameDt);
        intent.putExtra(EXTRA_password, passwordDt);
        intent.putExtra(EXTRA_birthday, birthdayDt);
        intent.putExtra(EXTRA_gender, genderDt);
        intent.putExtra(EXTRA_hobbies, hobbiesDt);
        startActivity(intent);

    }
    private void SelectDate(){
        EditText birthday = (EditText) findViewById(R.id.birtthdayEdit);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthday.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                selectedYear = year;
                selectedMonth = dayOfMonth;
                selectedDate = dayOfMonth;
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, dateSetListener, selectedYear, selectedMonth, selectedDate);
        datePickerDialog.show();

    }
    public void openDialog(String error){
        DialogMsg dialogMsg = new DialogMsg(error);
        dialogMsg.show(getSupportFragmentManager(), "Error message");
    }
}
package com.example.registerform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private int selectedYear, selectedMonth, selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void sendMessage(View v){

    }
}
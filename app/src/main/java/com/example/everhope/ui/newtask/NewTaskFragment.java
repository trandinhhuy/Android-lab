package com.example.everhope.ui.newtask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.everhope.EventInformation;
import com.example.everhope.MapsActivity;
import com.example.everhope.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class NewTaskFragment extends Fragment {

    private NewTaskViewModel newTaskViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_task, container, false);
        newTaskViewModel = new ViewModelProvider(this).get(NewTaskViewModel.class);
        TextView newTaskDateTime = (TextView) root.findViewById(R.id.new_task_date_time);
        EditText newTaskDate = (EditText) root.findViewById(R.id.new_task_date);
        TextView location = (TextView) root.findViewById(R.id.new_task_location_select);
        TextView interestField = (TextView) root.findViewById(R.id.new_task_interest_field);
        EditText editField = (EditText) root.findViewById(R.id.new_task_interest);
        newTaskDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] dt = {""};
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog((Context) getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dt[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                final Calendar c = Calendar.getInstance();
                                int mHour = c.get(Calendar.HOUR_OF_DAY);
                                int mMinute = c.get(Calendar.MINUTE);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {

                                                dt[0] +="   " + hourOfDay + ":" + minute;
                                                newTaskDate.setText(dt[0]);
                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                dt[0] ="";
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        interestField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String[] interestsArray = new String[]{"Children", "Environment", "Culture", "Education"};
                boolean[] checkedInterests = new boolean[]{false, false, false, false};
                String[] tempToken = {""};
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
        return root;
    }
}

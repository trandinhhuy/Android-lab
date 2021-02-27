package com.example.registerform;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidatorWithFormat implements DateValidator {
    private String format;

    public DateValidatorWithFormat(String format){
        this.format = format;
    }
    @Override
    public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat(this.format);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e){
            return false;
        }
        return true;
    }
}

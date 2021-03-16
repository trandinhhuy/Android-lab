package com.example.bt5;

import androidx.annotation.NonNull;

public class Student {
    String name;
    String course;
    double average;

    public Student(String name, String course, double average){
        this.average = average;
        this.name = name;
        this.course = course;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAverage() {
        return String.valueOf(average);
    }

    public String getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        String result = name + "-" + course + "-" + average;
        return  result;
    }
}

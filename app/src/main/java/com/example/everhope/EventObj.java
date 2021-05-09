package com.example.everhope;

public class EventObj {
    String ban, datetime, des, interest, llat, llong, location, name,organizer,phone;


    EventObj(String ban, String datetime, String des, String interest, String llat, String llong, String location, String name, String organier, String phone){
        this.ban = ban;
        this.datetime = datetime;
        this.des = des;
        this.interest = interest;
        this.llat = llat;
        this.llong=llong;
        this.location=location;
        this.name = name;
        this.organizer =organier;
        this.phone=phone;
    }

    public String getName() {
        return name;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getInterest() {
        return interest;
    }

    public String getLlat() {
        return llat;
    }

    public String getLlong() {
        return llong;
    }

    public String getLocation() {
        return location;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getPhone() {
        return phone;
    }

    public String getDes() {
        return des;
    }

    public String getBan() {
        return ban;
    }
}

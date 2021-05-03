package com.example.everhope;

public class ReportedObject {
    String date;
    String detail;
    String reason;
    String time;
    String userID;

    ReportedObject(){
        this.date = "";
        this. detail = "";
        this.reason="";
        this.time = "";
        this.userID = "";
    }
    ReportedObject(String date, String detail, String reason, String time, String userID){
        this.date = date;
        this. detail = detail;
        this.reason=reason;
        this.time = time;
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public String getDetail() {
        return detail;
    }

    public String getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }

    public String getUserID() {
        return userID;
    }
}

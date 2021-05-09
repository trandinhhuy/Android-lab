package com.example.everhope;

public class ReportedObject {
    String date;
    String detail;
    String reason;
    String time;
    String reported;
    String reportBy;
    String key;

    ReportedObject(){
        this.date = "";
        this. detail = "";
        this.reason="";
        this.time = "";
        this.reportBy = "";
        this.reported = "";
        this.key = "";
    }
    ReportedObject(String date, String detail, String reason, String time, String reportBy,String reported,String key){
        this.date = date;
        this. detail = detail;
        this.reason=reason;
        this.time = time;
        this.reportBy = reportBy;
        this.reported = reported;
        this.key = key;
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

    public String getReportBy() {   return reportBy;}

    public String getReported() {
        return reported;
    }

    public String getKey() { return key; }
}

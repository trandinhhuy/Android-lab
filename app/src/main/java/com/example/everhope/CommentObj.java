package com.example.everhope;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentObj {
    String content;
    String date;
    String time;
    String userID;

    CommentObj(String userID,String content){
        this.userID = userID;
        this.content = content;
        String[] currentDatetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()).split(" ");
        this.date = currentDatetime[0];
        this.time = currentDatetime[1];
    }

    public String getUserID() {
        return userID;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}

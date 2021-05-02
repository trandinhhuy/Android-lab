package com.example.everhope.supportClass;

public class UserRank {
    String userId;
    String userName;
    int totalEvent;

    public int compareTo (UserRank other){
        if (totalEvent > other.totalEvent){
            return 1;
        }
        if (totalEvent < other.totalEvent){
            return -1;
        }
        else return 0;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}

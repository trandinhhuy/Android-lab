package com.example.everhope.supportClass;

public class EventRank {
    String eventName;
    String eventId;
    int totalParticipant;

    public int compareTo (EventRank other){
        if (totalParticipant > other.totalParticipant){
            return 1;
        }
        if (totalParticipant < other.totalParticipant){
            return -1;
        }
        else return 0;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }
}

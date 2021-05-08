package com.example.everhope.supportClass;

import com.example.everhope.CommentObj;
import com.example.everhope.Event;
import com.example.everhope.EventObj;
import com.example.everhope.ReportedObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class UpdateFirebase {

    public static void updateData(String pathToNode, String data){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(pathToNode);
        myRef.setValue(data);
    }
    public static void updateReportedEvent(String path, ReportedObject reportedEvent){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        path+= "/"+uuid;
        firebaseDatabase.getReference(path+"/Detail").setValue(reportedEvent.getDetail());
        firebaseDatabase.getReference(path+"/Reason").setValue(reportedEvent.getReason());
        firebaseDatabase.getReference(path+"/Time").setValue(reportedEvent.getTime());
        firebaseDatabase.getReference(path+"/Date").setValue(reportedEvent.getDate());
        firebaseDatabase.getReference(path+"/User").setValue(reportedEvent.getUserID());
    }
    public static void removeData (String pathToNode){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(pathToNode);
        myRef.removeValue();
    }

    public static void updateNewEvent(String path, EventObj newEvent){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference(path+"/Ban").setValue(newEvent.getBan());
        firebaseDatabase.getReference(path+"/Datetime").setValue(newEvent.getDatetime());
        firebaseDatabase.getReference(path+"/Description").setValue(newEvent.getDes());
        firebaseDatabase.getReference(path+"/Location").setValue(newEvent.getLocation());
        firebaseDatabase.getReference(path+"/Lat").setValue(newEvent.getLlat());
        firebaseDatabase.getReference(path+"/Long").setValue(newEvent.getLlong());
        firebaseDatabase.getReference(path+"/Interest").setValue(newEvent.getInterest());
        firebaseDatabase.getReference(path+"/Name").setValue(newEvent.getName());
        firebaseDatabase.getReference(path+"/Organizer").setValue(newEvent.getOrganizer());
        firebaseDatabase.getReference(path+"/Phone").setValue(newEvent.getPhone());
    }

    public static void updateParticipant(String path, String userID, String type){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference(path+"/"+userID).setValue(type);
    }
    public static void updateComment(String path, CommentObj comment){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String randomCommentID = UUID.randomUUID().toString().replace("-", "");
        firebaseDatabase.getReference(path+"/"+randomCommentID+"/Content").setValue(comment.getContent());
        firebaseDatabase.getReference(path+"/"+randomCommentID+"/User").setValue(comment.getUserID());
        firebaseDatabase.getReference(path+"/"+randomCommentID+"/Time").setValue(comment.getTime());
        firebaseDatabase.getReference(path+"/"+randomCommentID+"/Date").setValue(comment.getDate());
    }
}

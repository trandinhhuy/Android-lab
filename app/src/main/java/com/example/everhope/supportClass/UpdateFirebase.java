package com.example.everhope.supportClass;

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
}

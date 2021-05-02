package com.example.everhope.supportClass;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateFirebase {
    public static void updateData(String pathToNode, String data){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(pathToNode);
        myRef.setValue(data);
    }
    public static void removeData (String pathToNode){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(pathToNode);
        myRef.removeValue();
    }
}

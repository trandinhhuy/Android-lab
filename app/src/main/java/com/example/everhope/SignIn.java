package com.example.everhope;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class SignIn extends Activity {
    TextView btnSignIn, btnGoToSignUp;
    EditText txtEmail, txtPw;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myloginpref", MODE_PRIVATE);
        Boolean isLogin = pref.getBoolean("isLogin", false);
        if (isLogin == true){
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
            finish();
        }

// path to database
        File storagePath = getApplication().getFilesDir();
        String myDBPath = storagePath +"/" + "EverHope";
        try {
            db = SQLiteDatabase.openDatabase(myDBPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            db = openOrCreateDatabase("EverHope", MODE_PRIVATE, null);
        } catch (SQLiteException e){
            e.printStackTrace();
        }

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPw = (EditText) findViewById(R.id.txtPassword);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        btnGoToSignUp = (TextView) findViewById(R.id.btnGoToSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String inputEmail = txtEmail.getText().toString();
                String inputPassword = txtPw.getText().toString();

                if (inputEmail.length()<1 || inputPassword.length()<1){
                    Toast.makeText(SignIn.this,"Please fill all required fields and try again.",Toast.LENGTH_LONG).show();
                }
                else if (!inputEmailIsValid(inputEmail)){
                    showDialog("Couldn't find your account. Please try again.");
                }
                else if (!inputPasswordIsValid(inputEmail,inputPassword)){
                    showDialog("Your password is invalid. Please try again.");
                }
                else{
                    final String[] ID = {"-1"};
                    final String[] banned = {"0"};
                    String email = txtEmail.getText().toString();
                    String password = SHA256.toSHA(txtPw.getText().toString());

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference().child("User");
                    myref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                String emailDb = String.valueOf(dataSnapshot.child("Email").getValue());
                                String passwordDb = String.valueOf(dataSnapshot.child("Password").getValue());

                                if (email.compareTo(emailDb) == 0 && password.compareTo(passwordDb) == 0){
                                    banned[0] = String.valueOf(dataSnapshot.child("Ban").getValue());
                                    String userKey = dataSnapshot.getKey();
                                    ID[0] = userKey;

                                }
                            }
                            SharedPreferences pref = getApplication().getSharedPreferences("myloginpref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            if (ID[0].compareTo("-1") != 0) {
                                if (banned[0].compareTo("1") == 0){
                                    showDialog("Your account has been banned. Please try another account.");
                                }
                                else {
                                    editor.putBoolean("isLogin", true);
                                    editor.putString("userID", ID[0]);
                                    editor.commit();
                                    Intent intent = new Intent(SignIn.this, MenuActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else {
                                showDialog("Your password is incorrect. Please try again.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        btnGoToSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public boolean inputEmailIsValid(String inputEmail){
        // nếu 'Email chưa được đăng ký' hoặc 'Tài khoản đã bị khóa' > false
        return true;
    }
    public boolean inputPasswordIsValid(String inputEmail, String inputPassword){
        // so sánh hash -> nếu 'Password không khớp" > false
        return true;
    }
    public void showDialog(String dialogMessage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

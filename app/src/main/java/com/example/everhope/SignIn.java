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
                    int ID = -1;
                    String email = txtEmail.getText().toString();
                    String password = SHA256.toSHA(txtPw.getText().toString());
                    Cursor cursor = db.rawQuery("Select * from Account", null);
                    while (cursor.moveToNext()){
                        String dbEmail = cursor.getString(0);
                        String dbPw = cursor.getString(1);
                        if (email.compareTo(dbEmail) == 0){
                            if (password.compareTo(dbPw) == 0){
                                Cursor userCursor = db.rawQuery("Select id from User join Account on Account.email = '" + email + "'", null);
                                while (userCursor.moveToNext()){
                                    ID = userCursor.getInt(0);
                                }
                            }
                        }
                    }
                    SharedPreferences pref = getApplication().getSharedPreferences("myloginpref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    if (ID >= 0) {
                        editor.putBoolean("isLogin", true);
                        editor.putInt("userID", ID);
                        editor.commit();
                        Intent intent = new Intent(SignIn.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        showDialog("Your password is incorrect. Please try again.");

                    }
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

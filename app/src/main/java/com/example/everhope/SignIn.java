package com.example.everhope;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends Activity {
    TextView btnSignIn, btnGoToSignUp;
    EditText txtEmail, txtPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPw = (EditText) findViewById(R.id.txtPassword);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        btnGoToSignUp = (TextView) findViewById(R.id.btnGoToSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String inputEmail = txtEmail.getText().toString();
                String inputPassword = txtPw.getText().toString();
/*
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
                    Intent intent = new Intent(SignIn.this, MenuActivity.class);
                    startActivity(intent);
                }*/
                Intent intent = new Intent(SignIn.this, MenuActivity.class);
                startActivity(intent);
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

package com.example.everhope;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SignUp extends Activity {
    TextView btnNextSignUp, btnGoToSignIn;
    EditText txtNewEmail,txtNewPw, txtRePw, txtPhone;

  //  TextView btnSignUp, btnResendOTP;
  //  FloatingActionButton btnCloseOTP;
  //  EditText txtOTP;

   // static boolean getStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        btnNextSignUp = (TextView)findViewById(R.id.btnSignUp);
        btnGoToSignIn = (TextView)findViewById(R.id.btnGoToSignIn);
        txtNewEmail = (EditText)findViewById(R.id.txtNewEmail);
        txtNewPw = (EditText)findViewById(R.id.txtNewPassword);
        txtRePw = (EditText)findViewById(R.id.txtRetypePassword);
        txtPhone = (EditText)findViewById(R.id.txtPhoneNumber);

        btnNextSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String newEmail = txtNewEmail.getText().toString();
                String newPw = txtNewPw.getText().toString();
                String rePw = txtRePw.getText().toString();
                String phone = txtPhone.getText().toString();

                if (newEmail.length()<1 || newPw.length()<1 || rePw.length()<1 || phone.length()<1){
                    Toast.makeText(SignUp.this,"Please fill all required fields and try again.",Toast.LENGTH_LONG).show();
                }
                else if (!inputEmailIsValid(newEmail)){
                    showDialog("This e-mail has already been registered.");
                }
                else if (!inputPasswordIsValid(newPw)){
                    showDialog("Your password is invalid. Please try again.");
                }
                else if (newPw.compareTo(rePw)!=0){
                    showDialog("Please make sure your passwords match.");
                }
                else {
                    //otp();
                    //if (getStarted){
                        // hoàn tất đăng ký -> mở intro lên
                    //}
                    Toast.makeText(SignUp.this,"Success!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplication(), SignIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnGoToSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(), SignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public boolean inputEmailIsValid(String inputEmail){
        // nếu 'Email đã đăng ký trước đó' > false
        return true;
    }
    public boolean inputPasswordIsValid(String pw){
        int len = pw.length();
        if (len<8 || len>16) return false;
        int count=0;
        for (int i=0; i<=9; i++)
            if (pw.contains(Integer.toString(i))) {
                ++count; break;
            }
        if (count==0) return false;
        count=0;
        for (int i=65; i<=90; i++)
            if (pw.contains(Character.toString((char)i)) || pw.contains(Character.toString((char)(i+32)))) {
                ++count; break;
            }
        if (count==0) return false;
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
    /*
    public void otp(){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view =  inflater.inflate(R.layout.sign_up_otp,null);
        ad.setView(view);
        ad.setCancelable(false);
        AlertDialog dialog = ad.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        btnSignUp = (TextView) view.findViewById(R.id.btnSignUpOTP);
        btnResendOTP = (TextView) view.findViewById(R.id.btnResend);
        btnCloseOTP = (FloatingActionButton) view.findViewById(R.id.btnCloseOTP);
        txtOTP = (EditText) view.findViewById(R.id.txtCode);

        btnCloseOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validOTP = "123"; // (test) lấy otp so sánh
                if (txtOTP.toString().compareTo(validOTP)==0){
                    getStarted = true;
                    dialog.dismiss();
                }
                else {
                    showDialog("Your OTP is invalid");
                }
            }
        });

        btnResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gửi mã mới (chưa code)
                // sau đó delay nút resend  30s
                btnResendOTP.setVisibility(view.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnResendOTP.setVisibility(view.VISIBLE);
                    }
                },5000); // test 5s
            }
        });
    }*/
}

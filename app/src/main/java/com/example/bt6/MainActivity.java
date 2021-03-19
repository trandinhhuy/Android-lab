package com.example.bt6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    ProgressBar horProgressBar;
    TextView loadingPercent;
    EditText inputText;
    Button againButton;
    int speed = 0;

    int globalVar = 0, accum = 0 , progressStep = 1;
    final int max_progress = 100;

    Handler myHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horProgressBar = (ProgressBar) findViewById(R.id.horProgressBar);
        inputText = (EditText) findViewById(R.id.inputText);
        againButton = (Button) findViewById(R.id.buttonAgain);
        loadingPercent = (TextView) findViewById(R.id.loadPercent);
        againButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
            }
        });
    }

    protected void Start() {
        againButton.setEnabled(false);
        accum = 0;
        horProgressBar.setMax(max_progress);
        horProgressBar.setProgress(0);
        horProgressBar.setVisibility(View.VISIBLE);
        speed = Integer.parseInt(String.valueOf(inputText.getText()));
        Thread myBackgroundThread = new Thread(backgroundTask, "backAlias1");
        myBackgroundThread.start();
    }
    private Runnable foreGroundRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                loadingPercent.setText(String.valueOf(globalVar) + "%");
                horProgressBar.incrementProgressBy(globalVar);
                accum ++;
                if (accum >= horProgressBar.getMax()){
                    horProgressBar.setVisibility(View.INVISIBLE);
                    againButton.setEnabled(true);
                }
            } catch (Exception e){
                Log.e("foreground task", e.getMessage());
            }
        }
    };
    private Runnable backgroundTask = new Runnable() {
        @Override
        public void run() {
            try {
                for (int i = 0 ; i < 100 ; i++){
                    Thread.sleep(9999 - speed * 10);
                    globalVar++;
                    myHandler.post(foreGroundRunnable);
                }
            }catch (InterruptedException e){
                Log.e("background interrupt", e.getMessage());
            }
        }
    };
}
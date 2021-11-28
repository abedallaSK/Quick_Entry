package com.example.startapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    public String email;
    public int typeAccount=-1;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "EMAIL";
    private final Handler mHideHandler = new Handler();


    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 100);

        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        email= mSettings.getString(DATA_TAG, "");
        typeAccount= mSettings.getInt("Type", -1);
        TextView textView=findViewById(R.id.welcome);
        textView.setText("welcome "+ email);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    StartActivity(typeAccount,email);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }


    public void StartActivity(int x,String email) {
        switch (x) {
            case 1:
                Intent intent = new Intent(this, UserMainActivity.class);
                intent.putExtra("Email", email);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, BusinessMainActivity.class);
                intent.putExtra("Email", email);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, ForeMainActivity.class);
                intent.putExtra("Email", email);
                startActivity(intent);
                break;

            default:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
        }

    }
}
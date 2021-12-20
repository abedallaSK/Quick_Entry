package com.example.startapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserChoiceActivity extends AppCompatActivity {

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
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 0);
        setContentView(R.layout.activity_user_choice);
    }
    public void NormalAccount(View view) {
        Intent intent=new Intent(this, Create_Account_Activity.class);
        intent.putExtra("Type",1);
        startActivity(intent);
    }

    public void BusinessAcount(View view) {
        Intent intent=new Intent(this, Create_Account_Activity.class);
        intent.putExtra("Type",2);
        startActivity(intent);
    }

    public void ForemanAccount(View view) {
        Intent intent=new Intent(this, Create_Account_Activity.class);
        intent.putExtra("Type",3);
        startActivity(intent);
    }


    public void help(View view) {

        startActivity(new Intent(this,HelpActivity.class));
    }
}
package com.example.startapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.startapplication.classes.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Created by abedalla
public class StartActivity extends AppCompatActivity {

    public String key;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");

    public int typeAccount=-1;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private final Handler mHideHandler = new Handler();


    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
    };



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 0);

        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
         key= mSettings.getString(DATA_TAG, "");

        typeAccount= mSettings.getInt("Type", -1);
        TextView textView=findViewById(R.id.welcome);

// Read from the database
        if(key.length()>1) {
            myRef.child(key).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Account account = dataSnapshot.getValue(Account.class);
                    if(account!=null)
                    textView.setText("welcome "+ account.getName()+" "+account.getPhoneNumber());
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                    textView.setText("Offline mood");
                }
            });
        }else textView.setText("welcome");
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                    StartActivity(typeAccount);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }


    public void StartActivity(int x) {
        switch (x) {
            case 1:
                Intent intent = new Intent(this, UserMainActivity.class);
                intent.putExtra("KEY", key);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, BusinessMainActivity.class);
                intent.putExtra("KEY", key);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, ListformarActivity.class);
                intent.putExtra("KEY",key);
                startActivity(intent);
                break;

            default:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
        }

    }
}
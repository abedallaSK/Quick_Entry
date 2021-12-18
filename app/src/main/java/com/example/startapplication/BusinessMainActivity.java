package com.example.startapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.startapplication.classes.Account;
import com.example.startapplication.databinding.ActivityBusinessMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BusinessMainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private String key;
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
        setContentView(R.layout.activity_business_main);


        TextView tvName=findViewById(R.id.tv_name_business);
        TextView tvUsername=findViewById(R.id.tv_username_business);
        ImageView imProfile=findViewById(R.id.imageView17_business);
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 0);
        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        key = mSettings.getString(DATA_TAG, "");
        if (key.equals("")) goLogin();
        if (key != null) {
            myRef.child(key).addValueEventListener(new ValueEventListener() {
                @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Account account = dataSnapshot.getValue(Account.class);
                    if (account != null) {
                        tvName.setText(account.getName() + " " + account.getFamilyName());
                       tvUsername.setText(account.getId());
                        Picasso.get().load(account.getProfileUri()).into(imProfile);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
    }
    public void DoorList(View view) {
        startActivity(new Intent(this,BusinessDoorActivity.class));
    }

    public void logOut (View view){
        AlertDialog.Builder myAlertBuilder=new AlertDialog.Builder(BusinessMainActivity.this);
        myAlertBuilder.setTitle("Logout");
        myAlertBuilder.setMessage("Are you sure to logout");
        myAlertBuilder.setIcon(R.drawable.ic_baseline_login_24);
        myAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               goLogin();
            }
        });
        myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        myAlertBuilder.show();

    }
    public void goLogin()
    {
        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void profile(View view) {
        Intent intent=new Intent(this,ProfileActivity.class);
        intent.putExtra("KEY",key);
        startActivity(intent);
    }
}
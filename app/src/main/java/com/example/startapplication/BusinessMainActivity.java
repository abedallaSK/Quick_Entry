package com.example.startapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.startapplication.ui.main.SectionsPagerAdapter;
import com.example.startapplication.databinding.ActivityBusinessMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BusinessMainActivity extends AppCompatActivity {

    private ActivityBusinessMainBinding binding;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "EMAIL";
    private ImageView imageView;
    private String email;
    private DatabaseReference doorRef = FirebaseDatabase.getInstance().getReference("Doors");
    private List<String> doorName=new ArrayList();
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBusinessMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        email= mSettings.getString(DATA_TAG, "");
        //  if(email=="") logOut2(null);
        doorName.add("Door_1");

        imageView=findViewById(R.id.imageView7);
        Picasso.get().load(R.drawable.open).into(imageView);
        counter=0;
        doorRef.child(email).child(doorName.get(0)).child("counter").setValue(counter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        doorRef.child(email).child(doorName.get(0)).child("status").setValue(false);
        doorRef.child(email).child(doorName.get(0)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                boolean value = dataSnapshot.child("status").getValue(boolean.class);
                counter=dataSnapshot.child("counter").getValue(Integer.class);
                Log.d(TAG, "Value is: " + value);
                if (value) {
                    Picasso.get().load(R.drawable.close).into(imageView);
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    Picasso.get().load(R.drawable.open).into(imageView);
                                    doorRef.child(email).child(doorName.get(0)).child("status").setValue(false);
                                }
                            },
                            5000);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    public void logOut2(View view) {
        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void OpenDoor(View v) {

        doorRef.child(email).child(doorName.get(0)).child("status").setValue(true);
        counter++;
        doorRef.child(email).child(doorName.get(0)).child("counter").setValue(counter);



        /*Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();*/
        //
    }
}
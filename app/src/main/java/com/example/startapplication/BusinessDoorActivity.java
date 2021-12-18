package com.example.startapplication;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.startapplication.classes.Account;
import com.example.startapplication.databinding.ActivityUserBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.startapplication.ui.main.SectionsPagerAdapter;
import com.example.startapplication.databinding.ActivityBusinessDoorBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BusinessDoorActivity extends AppCompatActivity {

    private ActivityBusinessDoorBinding binding;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private String key ;
    private DatabaseReference doorRef = FirebaseDatabase.getInstance().getReference("Doors");
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private List<String> doorName = new ArrayList();
    private TextView tital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusinessDoorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        key = mSettings.getString(DATA_TAG, "");
        if(key=="") logOut();
        else {
        doorName.add("Door_1");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        tital=findViewById(R.id.title);


        }
        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Account account=snapshot.getValue(Account.class);
                if(account!=null) {
                   tital.setText(account.getUsername());
                   ImageView imageView=findViewById(R.id.imageView17);
                   Picasso.get().load(account.getProfileUri()).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_business_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.main_logout:
                logOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logOut (){
            SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(this, LoginActivity.class));
    }




    public void addDoor(View view) {
        Toast.makeText(this,"coming soon",Toast.LENGTH_SHORT).show();
    }
    public String getKey()
    {
        return key;
    }

    public void goProfile(View view) {
        Intent intent=new Intent(this,ProfileActivity.class);
        intent.putExtra("KEY",key);
        startActivity(intent);
    }
}
package com.example.startapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.startapplication.databinding.ActivityFormanMainBinding;

public class Foreman_Main_Activity extends AppCompatActivity {

    private ActivityFormanMainBinding binding;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFormanMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        key= mSettings.getString(DATA_TAG, "Cash");
        if(key.equals("Cash"))
            logOut();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#3A3A3A"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        BottomNavigationView navView = findViewById(R.id.nav_view_foreman);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_foreman, R.id.navigation_dashboard_foreman, R.id.navigation_notifications_foreman)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_forman_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewForeman, navController);
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
            case R.id.action_settings:
                goToprofile();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToprofile() {
        Intent intent=new Intent(this,ProfileActivity.class);
        intent.putExtra("KEY",key);
        startActivity(intent);
    }

    public void logOut (){
        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this, LoginActivity.class));
    }


}
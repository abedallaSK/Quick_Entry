package com.example.startapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.startapplication.ui.main.SectionsPagerAdapter;
import com.example.startapplication.databinding.ActivityForeMainBinding;

public class ForeMainActivity extends AppCompatActivity {

    private ActivityForeMainBinding binding;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "EMAIL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForeMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void logOut3(View view) {
        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this,LoginActivity.class));
    }
}
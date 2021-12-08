package com.example.startapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


import com.example.startapplication.classes.Account;
import com.example.startapplication.classes.ListAdapter2;
import com.example.startapplication.databinding.ActivityListformarBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//Created by yosef
public class ListformarActivity extends AppCompatActivity {

    ActivityListformarBinding binding;
    private static final String PREFS_NAME = "LOGIN";
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private ArrayList<Account> list_of_Account = new ArrayList<>();
   // private ArrayList<String> list_of_Key = new ArrayList<>();
    private ListAdapter2 listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListformarBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_listformar);
        setContentView(binding.getRoot());

// Read from the database

//update
        listViewOnline(myRef);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListformarActivity.this, UserActivity.class);
                i.putExtra("KEY", list_of_Account.get(position).getKey());
                startActivity(i);
            }
        });
    }

    public void listViewOnline(DatabaseReference dRef) {

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<Account> set = new HashSet<>();
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    String key=i.next().getKey();
                    set.add((dataSnapshot.child(key)).getValue(Account.class));
                }

                list_of_Account.clear();
                list_of_Account.addAll(set);
                // arrayAdapter.notifyDataSetChanged();
                listAdapter = new ListAdapter2(ListformarActivity.this, list_of_Account);
                binding.listview.setAdapter(listAdapter);
                binding.listview.setClickable(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
}
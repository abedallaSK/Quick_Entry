package com.example.startapplication;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;

import com.example.startapplication.classes.Account;
import com.example.startapplication.classes.DoorUser;
import com.example.startapplication.classes.ListDoorUser;
import com.example.startapplication.classes.ListUserDoorAdapter;
import com.example.startapplication.databinding.ActivityUserBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BusinessDoorActivity extends AppCompatActivity implements
        NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {

    private ActivityBusinessDoorBinding binding;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private static final String LOCK_TAG = "LOCK";
    private String key ;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private DatabaseReference doorRef = FirebaseDatabase.getInstance().getReference("Doors");
    private List<String> doorName = new ArrayList();
    private TextView tital;
    NfcAdapter nfcAdapter;
    String keyuser;
    private ListDoorUser listDoorUser;
    private Integer counter;
    private boolean islock;
    //private  ArrayList<DoorUser> doorUsers=new ArrayList<>();
    private ArrayList<DoorUser> doorUsers=new ArrayList<>();
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
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if(nfcAdapter==null){
                Toast.makeText(this,
                        "nfcAdapter==null, no NFC adapter exists",
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,
                        "Set Callback(s)",
                        Toast.LENGTH_LONG).show();
                nfcAdapter.setNdefPushMessageCallback(this, this);
                nfcAdapter.setOnNdefPushCompleteCallback(this, this);
            }
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        tital=findViewById(R.id.title);
            doorRef.child(key).child(doorName.get(0)).child("online").setValue(  new StringBuilder().append(+Calendar.getInstance().getTime().getDate()).append("/").append(Calendar.getInstance().getTime().getMonth()).append("/").append(Calendar.getInstance().getTime().getSeconds()).append("                 ").append(Calendar.getInstance().getTime().getHours()).append(":").append(Calendar.getInstance().getTime().getMinutes()).append(":").append(Calendar.getInstance().getTime().getSeconds()).toString());
        doorRef.child(key).child(doorName.get(0)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDoorUser= dataSnapshot.child("list").getValue(ListDoorUser.class);
                Integer  value= dataSnapshot.child("counter").getValue(Integer.class);
                if(value==null) counter=0;
                else counter=value;

                 Boolean value1= dataSnapshot.child("islock").getValue(Boolean.class);
                if(value1==null) islock=false;
                else islock=value1;

                if(listDoorUser==null) {
                    listDoorUser = new ListDoorUser();
                    counter=0;
                    islock=false;
                }
                doorUsers=listDoorUser.getDoorUsers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
        AlertDialog.Builder myAlertBuilder=new AlertDialog.Builder(BusinessDoorActivity.this);
        myAlertBuilder.setTitle("Logout");
        myAlertBuilder.setMessage("Are you sure to logout");
        myAlertBuilder.setIcon(R.drawable.ic_baseline_login_24);
        myAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences mSettings =BusinessDoorActivity.this.getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = mSettings.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(BusinessDoorActivity.this, LoginActivity.class));
            }
        });
        myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        myAlertBuilder.show();
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

    @Override
    protected void onResume() {
        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
       islock = mSettings.getBoolean(LOCK_TAG,false);
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
                    Parcelable[] parcelables =
                            intent.getParcelableArrayExtra(
                                    NfcAdapter.EXTRA_NDEF_MESSAGES);
                    NdefMessage inNdefMessage = (NdefMessage) parcelables[0];
                    NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
                    NdefRecord NdefRecord_0 = inNdefRecords[0];
                    keyuser = new String(NdefRecord_0.getPayload());
                    //textOut.setText(keyuser);

                    if (keyuser != null) {
                        if (keyuser.equals("NO"))
                            Toast.makeText(this, "this user don't have green card yet", Toast.LENGTH_SHORT).show();
                        else {
                            if (islock) {
                                myRef.child(keyuser).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // This method is called once with the initial value and again
                                        // whenever data at this location is updated.
                                        Account account = dataSnapshot.getValue(Account.class);
                                        doorRef.child(key).child("Door_1").child("status").setValue(true);
                                        if (account.getType() == 1) {
                                            if (account != null) {
                                                doorRef.child(key).child("Door_1").child("status").setValue(true);
                                                doorRef.child(key).child("Door_1").child("counter").setValue(++counter);

                                                DoorUser doorUser = new DoorUser(account.getName(), account.getKey(),
                                                        new StringBuilder().append(+Calendar.getInstance().getTime().getDate()).append("/").append(Calendar.getInstance().getTime().getMonth()).append("/").append(Calendar.getInstance().getTime().getYear()).append("                 ").append(Calendar.getInstance().getTime().getHours()).append(":").append(Calendar.getInstance().getTime().getMinutes()).append(":").append(Calendar.getInstance().getTime().getSeconds()).toString(), ++counter);
                                                listDoorUser.addUser(doorUser);
                                                doorRef.child(key).child("Door_1").child("list").setValue(listDoorUser);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        // Failed to read value

                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {

        final String eventString = "onNdefPushComplete\n" + event.toString();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        eventString,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }
}
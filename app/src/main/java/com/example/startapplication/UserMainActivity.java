package com.example.startapplication;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.startapplication.classes.Account;
import com.google.android.gms.common.api.Status;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.startapplication.databinding.ActivityUserMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;

public class UserMainActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityUserMainBinding binding;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private Account account;


    //
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private String key="Cash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this,"Sorry this device does not have NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }

        mAdapter.setNdefPushMessageCallback(this, this);


        setSupportActionBar(binding.appBarUserMain.toolbar);
        binding.appBarUserMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        key= mSettings.getString(DATA_TAG, "Cash");
        if(key.equals("Cash"))
            logOut();
      //  TextView textView=findViewById(R.id.textViewName);
       // textView.setText( key);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navViewHome;
        View header=navigationView.getHeaderView(0);
        TextView nameView = (TextView)header.findViewById(R.id.Name_Main);
        TextView emailView = (TextView)header.findViewById(R.id.Email_Main);
        ImageView imageView =header.findViewById(R.id.imageView_mainProfile);

            assert key != null;
            myRef.child(key).addValueEventListener(new ValueEventListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    account=dataSnapshot.getValue(Account.class);
                    if(account!=null) {
                        nameView.setText(account.getName());
                        emailView.setText(account.getEmail());
                        Picasso.get().load(account.getProfileUri()).resize(256,256).into(imageView);
                        TextView tvstatus=findViewById(R.id.tvstatus2);
                        ImageView imageView1=findViewById(R.id.imgstatus2);
                        if (account.getCheckGreen() == 0) {
                            imageView1.setImageResource(R.drawable.ic_baseline_access_time_24);
                            tvstatus.setText("in sight");
                            tvstatus.setTextColor(Color.parseColor("#FFFF00"));
                        } else if (account.getCheckGreen() == 1) {
                            imageView1.setImageResource(R.drawable.ic_baseline_done_24);

                            tvstatus.setText("OK");
                            tvstatus.setTextColor(Color.parseColor("#00FF00"));
                            TextView textView2=findViewById(R.id.textView7);
                            textView2.setText(account.getDate());

                        } else if (account.getCheckGreen() == 2) {
                            imageView1.setImageResource(R.drawable.ic_baseline_clear_24);
                            tvstatus.setText("\n\n\nyour green card not right\n\n");
                            tvstatus.setTextColor(Color.parseColor("#FF0000"));
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");
// set Fragmentclass Arguments
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
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
                goProfileUser(null);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean logOut() {
        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this,LoginActivity.class));
        return false;
    }


    public String getKey()
    {
        return  key;
    }

    public void goProfileUser(View view) {
            Intent intent=new Intent(this,ProfileActivity.class);
            intent.putExtra("KEY",key);
            startActivity(intent);
    }
    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {

        if(account.getCheckGreen()!=1)
        {
            Toast.makeText(this,"you dont have courrect green card you cant scan in nfc",Toast.LENGTH_SHORT).show();
            return new NdefMessage(NdefRecord.createMime("text/plain","NO".getBytes()));
        }else {
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain",key.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;}

    }
}
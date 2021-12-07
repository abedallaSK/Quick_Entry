package com.example.startapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.startapplication.classes.Account;
import com.example.startapplication.databinding.ActivityUserBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {
    ActivityUserBinding binding;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        binding =ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent =this.getIntent();
        if (intent != null){

            String key = intent.getStringExtra("KEY");

            // Read from the database
            myRef.child(key).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Account account = dataSnapshot.getValue(Account.class);
                    binding.nameProfile.setText(account.getName()+" "+account.getFamilyName());
                    binding.phoneProfile.setText(account.getId());
                    binding.countryProfile.setText(account.getType()+"");
                    Picasso.get().load(account.getProfileUri()).into(binding.imageViewMainProfile);
                    Picasso.get().load(account.getGreenUri()).into(binding.imageView6);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }
    }
}
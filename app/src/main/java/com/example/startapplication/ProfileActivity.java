package com.example.startapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.startapplication.classes.Account;
import com.example.startapplication.databinding.ActivityProfileBinding;
import com.example.startapplication.databinding.ActivityUserBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");

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
        setContentView(R.layout.activity_profile);
        binding =ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 0);

        binding.tvName.setEnabled(false);
        binding.tvName.setEnabled(false);
        binding.edEmail.setEnabled(false);
        binding.edPhone.setEnabled(false);

        String key=getIntent().getStringExtra("KEY");
        if(key!=null) {
            myRef.child(key).addValueEventListener(new ValueEventListener() {
                @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Account account = dataSnapshot.getValue(Account.class);
                    if (account != null) {
                        binding.tvName.setText(account.getName() + " " + account.getFamilyName());
                        binding.tvUsername.setText(account.getId());
                        Picasso.get().load(account.getProfileUri()).into(binding.imageView17);
                        binding.edEmail.setText(account.getEmail());
                        binding.edPhone.setText(account.getPhoneNumber());
                       if(account.getType()==1)
                           Picasso.get().load(account.getGreenUri()).into(binding.imgProfile);

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

    public void edit(View view) {

        if(binding.button.getText()=="edit")
        {
            binding.tvName.setEnabled(true);
            binding.tvName.setEnabled(true);
            binding.edEmail.setEnabled(true);
            binding.edPhone.setEnabled(true);
            binding.button.setText("save");

        }else {binding.button.setText("edit");
            binding.tvName.setEnabled(false);
            binding.tvName.setEnabled(false);
            binding.edEmail.setEnabled(false);
            binding.edPhone.setEnabled(false);
       }

    }
}
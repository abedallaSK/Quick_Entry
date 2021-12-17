package com.example.startapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    private  String key;
    private boolean status ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        binding =ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent =this.getIntent();
        if (intent != null){

            key = intent.getStringExtra("KEY");

            // Read from the database
            myRef.child(key).addValueEventListener(new ValueEventListener() {
                @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Account account = dataSnapshot.getValue(Account.class);
                    if (account != null) {
                        binding.nameProfile.setText(account.getName() + " " + account.getFamilyName());
                        binding.phoneProfile.setText(account.getId());
                        Picasso.get().load(account.getProfileUri()).resize(512,512).into(binding.imageViewMainProfile);
                        if (account.getType() == 1) {
                            Picasso.get().load(account.getGreenUri()).into(binding.imageView6);
                            binding.countryProfile.setText("normal user");
                            if (account.getCheckGreen() == 0) {
                                binding.imgstatus.setImageResource(R.drawable.ic_baseline_access_time_24);
                                binding.tvstatus.setTextColor(R.color.yellow);
                                binding.tvstatus.setText("in sight");
                            } else if (account.getCheckGreen() == 1) {
                                binding.imgstatus.setImageResource(R.drawable.ic_baseline_done_24);
                                binding.tvstatus.setTextColor(R.color.green);
                                binding.tvstatus.setText("OK");
                                binding.editTextDateUser.setHint(account.getDate());

                            } else if (account.getCheckGreen() == 2) {
                                binding.imgstatus.setImageResource(R.drawable.ic_baseline_clear_24);
                                binding.tvstatus.setTextColor(R.color.red);
                                binding.tvstatus.setText("OK");
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
                                                   }
        binding.switchUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                status=isChecked;


            }
        });
            binding.Cansel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserActivity.this,Foreman_Main_Activity.class));
                }
            });


    }

    public void delete(View view) {
        AlertDialog.Builder myAlertBuilder=new AlertDialog.Builder(UserActivity.this);
        myAlertBuilder.setTitle("Delete");
        myAlertBuilder.setMessage("Are you sure to delete?");
        myAlertBuilder.setIcon(R.drawable.ic_baseline_delete_24);
        myAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myRef.child(key).removeValue();
                startActivity(new Intent(UserActivity.this, Foreman_Main_Activity.class));
            }
        });
        myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        myAlertBuilder.show();
    }

    public void save(View view) {
        AlertDialog.Builder myAlertBuilder=new AlertDialog.Builder(UserActivity.this);
        myAlertBuilder.setTitle("Are you sure to save?");
        if( status)
            myAlertBuilder.setMessage("are you want to conform the green card ?");
        else  myAlertBuilder.setMessage("are you want to  not conform the green card ?");
        myAlertBuilder.setIcon(R.drawable.ic_baseline_save_24);
        myAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(status)
                    myRef.child(key).child("checkGreen").setValue(1);
                else myRef.child(key).child("checkGreen").setValue(2);
            }
        });
        myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        myAlertBuilder.show();

    }
}
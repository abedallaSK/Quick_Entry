package com.example.startapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.startapplication.classes.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_Email;
    private EditText editText_PassWord;

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private ProgressBar progressBar;
     List<Account> accounts=new ArrayList<>();
    //
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    //
    private final Handler mHideHandler = new Handler();
    private Button btSing;
   // private Boolean isOk=false;
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
        setContentView(R.layout.activity_login);
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 0);
        editText_Email = findViewById(R.id.editTextEmail);
        editText_PassWord = findViewById(R.id.editTextPassword);
        btSing=findViewById(R.id.sing_In);
        btSing.setEnabled(false);
        progressBar=findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.INVISIBLE);

        editText_Email.addTextChangedListener(loginTextWatcher);
        editText_PassWord.addTextChangedListener(loginTextWatcher);


        //getdata
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                //isOk =false;
                while (i.hasNext()) {
                    String ke=i.next().getKey();
                    accounts.add(dataSnapshot.child(ke).getValue(Account.class));
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"No Internet ",Toast.LENGTH_SHORT).show();
            }
        });
    }



    private final TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edNameInput =editText_Email.getText().toString().trim();
            String edLastNameInput =editText_PassWord.getText().toString().trim();
            btSing.setEnabled(!edNameInput.isEmpty() && !edLastNameInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };



    public void Create(View view) {
        Intent i=new Intent(this, UserChoiceActivity.class);
        startActivity(i);
    }


    public void SingIn(View view) {
        String name=editText_Email.getText().toString();
        String password=editText_PassWord.getText().toString();
        int index=-1;
        index=IsCorrectUser(name,password);
        if(index>=0) GoToActivity(index);
        else  Toast.makeText(getApplicationContext(),"The Password or name Error ",Toast.LENGTH_SHORT).show();
    }


    public int IsCorrectUser(String name,String password)
    {
        for(int i=0;i<accounts.size();i++)
        {
            if(accounts.get(i).getUsername().equals(name) && accounts.get(i).getPassword().equals(password))
                return i;
        }
        return -1;
    }

   public int GoToActivity(int x) {

        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
       SharedPreferences.Editor editor = mSettings.edit();
       editor.putString(DATA_TAG, accounts.get(x).getKey());
       editor.putInt("Type",accounts.get(x).getType());
       editor.commit();
        switch (accounts.get(x).getType()) {
            case 1:
                startActivity(new Intent(this, UserMainActivity.class));
                //for test
                return 1;
                //break;
            case 2:
                startActivity(new Intent(this, BusinessMainActivity.class));
                //for test
                return 2;

            case 3:
                startActivity(new Intent(this, Foreman_Main_Activity.class));
                //for test
                return 3;

        }
    // for test
       return 0;
   }

    public Intent ForgetPassword(View view) {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
        return intent;
    }

}
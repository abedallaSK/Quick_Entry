package com.example.startapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText editText_Email;
    private EditText editText_Username;
    private EditText edPasswordShow;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private String key;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
     private Spinner spinner;

    List<Account> accounts=new ArrayList<>();
    private final Handler mHideHandler = new Handler();
    private Button btcheckPassword;
    private Button btSaved;

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
        setContentView(R.layout.activity_forget_passowrd);
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 0);
        editText_Email = findViewById(R.id.editTextTextPersonName3);
        editText_Username = findViewById(R.id.editTextTextPersonName2);
        edPasswordShow=findViewById(R.id.passwordShow);
        btcheckPassword =findViewById(R.id.checkPassword);
        btSaved=findViewById(R.id.savedPassword);
        btSaved.setVisibility(View.INVISIBLE);
        btcheckPassword.setEnabled(false);
        edPasswordShow.setVisibility(View.INVISIBLE);

        spinner = findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editText_Email.addTextChangedListener(loginTextWatcher);
        editText_Username.addTextChangedListener(loginTextWatcher);



        //getdata
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                //isOk =false;
                while (i.hasNext()) {
                    String ke=i.next().getKey();
                    accounts.add(dataSnapshot.child(ke).getValue(Account.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
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
            String edLastNameInput =editText_Username.getText().toString().trim();
            btcheckPassword.setEnabled(!edNameInput.isEmpty() && !edLastNameInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void Check(View view) {
        btSaved.setVisibility(View.INVISIBLE);
        edPasswordShow.setVisibility(View.INVISIBLE);
        String email = editText_Email.getText().toString();
        String username = editText_Username.getText().toString();
        int type = spinner.getSelectedItemPosition() + 1;
        boolean flag =false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals(username) && accounts.get(i).getEmail().equals(email) && accounts.get(i).getType() == type) {
                key = accounts.get(i).getKey();
                edPasswordShow.setText(accounts.get(i).getPassword());
                edPasswordShow.setVisibility(View.VISIBLE);
                btSaved.setVisibility(View.VISIBLE);
                flag=true;
            }
        }
       if(!flag) Toast.makeText(getApplicationContext(), "The Email or username Error ", Toast.LENGTH_SHORT).show();
    }


    public void Save(View view) {
        if(edPasswordShow.getText().toString().length()>5)
        {
            myRef.child(key).child("password").setValue(edPasswordShow.getText().toString());
            Toast.makeText(this, "Saved new Password", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "the password must to be more then 5", Toast.LENGTH_SHORT).show();

    }

    public void help(View view) {
        Toast.makeText(this, "connect with us", Toast.LENGTH_SHORT).show();
    }




}
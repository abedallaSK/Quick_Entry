package com.example.startapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.Iterator;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText editText_Email;
    private EditText editText_Username;
    private EditText edPasswordShow;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private String key;



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


    Spinner spinner;
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
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editText_Email.addTextChangedListener(loginTextWatcher);
        editText_Username.addTextChangedListener(loginTextWatcher);

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
        String email=editText_Email.getText().toString();
        String username= editText_Username.getText().toString();
        int type=spinner.getSelectedItemPosition()+1;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                boolean x=true;
                while (i.hasNext()) {
                    String ke=i.next().getKey();
                    Account account=dataSnapshot.child(ke).getValue(Account.class);

                    if(account.getEmail()!=null &&account.getUsername()!=null &&account.getPassword() !=null) {
                        if ((account.getEmail().equals(email) && account.getUsername().equals(username)) && account.getType()==type) {
                            x=false;
                            key=account.getKey();
                            edPasswordShow.setText(account.getPassword());
                            edPasswordShow.setVisibility(View.VISIBLE);
                            btSaved.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
                if(x) Toast.makeText(getApplicationContext(),"The Email or username Error ",Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }


    public void Save(View view) {
        if(edPasswordShow.getText().toString().length()>5)
        {
            myRef.child(key).child("password").setValue(edPasswordShow.getText().toString());
            Toast.makeText(this, "Saved new Password", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "the password must to be more then 5", Toast.LENGTH_SHORT).show();

    }
}
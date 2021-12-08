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

import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_Email;
    private EditText editText_PassWord;

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private ProgressBar progressBar;

    //
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    //
    private final Handler mHideHandler = new Handler();
    private Button btSing;

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

      //  Toast.makeText(getApplicationContext(),"for foreman Test the Username is abedalla2 and the passowrd is 123456789 or you can click in the button  ",Toast.LENGTH_SHORT).show();


        editText_Email.addTextChangedListener(loginTextWatcher);
        editText_PassWord.addTextChangedListener(loginTextWatcher);

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



    public String Create(View view) {
        startActivity(new Intent(this, UserChoiceActivity.class));
        //for test
        return  "OK";
    }

    public void SingIn(View view) {
        String email=editText_Email.getText().toString();
        String password=editText_PassWord.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                boolean x=true;
                while (i.hasNext()) {
                    String ke=i.next().getKey();
                   Account account=dataSnapshot.child(ke).getValue(Account.class);

                   if(account.getEmail()!=null &&account.getUsername()!=null &&account.getPassword() !=null) {
                       if ((account.getEmail().equals(email) || account.getUsername().equals(email)) && account.getPassword().equals(password)) {
                           x=false;
                           StartActivity(account.getType(), ke);
                           break;
                       }
                   }
                }
               if(x) Toast.makeText(getApplicationContext(),"The Password or name Error ",Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
            }

        });
        progressBar.setVisibility(View.INVISIBLE);

    }



   public String StartActivity(int x, String Code) {

        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
       SharedPreferences.Editor editor = mSettings.edit();
       editor.putString(DATA_TAG, Code);
       editor.putInt("Type",x);
       editor.commit();
        switch (x) {
            case 1:
                Intent intent = new Intent(this, UserMainActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, BusinessMainActivity.class);
                //intent.putExtra("Email", email);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, ListformarActivity.class);
                //intent.putExtra("Code", Code);
                startActivity(intent);
                break;

        }
    // for test
       return "OK";
   }

    public void ForgetPassword(View view) {
    }

    public void user2(View view) {
        Intent intent = new Intent(this, ListformarActivity.class);
        startActivity(intent);
    }
}
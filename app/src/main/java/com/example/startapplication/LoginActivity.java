package com.example.startapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    //
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "Code";
    //




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_Email = findViewById(R.id.editTextEmail);
        editText_PassWord = findViewById(R.id.editTextPassword);
        setTitle("welcome");

        Toast.makeText(getApplicationContext(),"for foreman Test the Username is abedalla2 and the passowrd is 123456789 or you can click in the button  ",Toast.LENGTH_SHORT).show();

    }





    public String Create(View view) {
        Intent i=new Intent(this, UserChoiceActivity.class);
        startActivity(i);
        //for test
        return  "OK";
    }

    public Object SingIn(View view) {
        String email=editText_Email.getText().toString();
        String password=editText_PassWord.getText().toString();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                   Account account=(i.next()).getValue(Account.class);
                   if(account.getEmail()!=null &&account.getUsername()!=null &&account.getPassword() !=null) {
                       if ((account.getEmail().equals(email) || account.getUsername().equals(email)) && account.getPassword().equals(password)) {
                           String ke = i.next().getKey();
                           StartActivity(account.getType(), ke);
                           break;
                       }
                   }
                }
                Toast.makeText(getApplicationContext(),"The Password or name Error ",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
        return null;
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
                intent.putExtra("Code", Code);
                startActivity(intent);
                break;
            case 2:
                Toast.makeText(this,"coming soon",Toast.LENGTH_SHORT).show();
                /*intent = new Intent(this, BusinessMainActivity.class);
                intent.putExtra("Email", email);
                startActivity(intent);*/
                break;
            case 3:
                intent = new Intent(this, ListformarActivity.class);
                intent.putExtra("Code", Code);
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
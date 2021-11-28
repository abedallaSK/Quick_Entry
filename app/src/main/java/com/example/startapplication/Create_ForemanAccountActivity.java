package com.example.startapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_ForemanAccountActivity extends AppCompatActivity {
    private EditText edName;
    private EditText edLastName;
    private EditText edId;
    private EditText edPhone;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edRe_Password;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");

    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_forman_account);
        setContentView(R.layout.activity_create_forman_account);
        edName = findViewById(R.id.formanName);
        edEmail = findViewById(R.id.ForMainEmail);
        edPassword = findViewById(R.id.formanPassword);

    }
    public void ForemanAccount(View view) {
        if (edName.getText().length() > 1 && edEmail.getText().length() > 1 && edPassword.getText().length() > 1) {
            myRef = myRef.child(edEmail.getText().toString());
            myRef.child("Name").setValue(edName.getText().toString());
            myRef.child("Password").setValue(edPassword.getText().toString());
            myRef.child("Email").setValue(edEmail.getText().toString());
            myRef.child("Type").setValue(3);


            SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(DATA_TAG, edEmail.getText().toString());
            editor.putInt("Type", 3);
            editor.commit();
            Intent intent = new Intent(this, ForeMainActivity.class);
            intent.putExtra("Email", edEmail.getText().toString());
            startActivity(intent);
        }
    }
}
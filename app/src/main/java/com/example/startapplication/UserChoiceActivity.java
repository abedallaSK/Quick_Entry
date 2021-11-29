package com.example.startapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class UserChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choice);
    }
    public void NormalAccount(View view) {
        startActivity(new Intent(this, Create_Account_Activity.class));
    }

    public void BusinessAcount(View view) {
        Toast.makeText(this,"coming soon",Toast.LENGTH_SHORT).show();
        /*startActivity(new Intent(this, Create_BusinessAccount_Activity.class));*/
    }

    public void ForemanAccount(View view) {
        Toast.makeText(this,"coming soon",Toast.LENGTH_SHORT).show();
       /* startActivity(new Intent(this, Create_ForemanAccountActivity.class));*/
    }

}
package com.example.startapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Iterator;

public class Create_Account_Activity extends AppCompatActivity {

    private EditText edName;
    private EditText edLastName;
    private EditText edId;
    private EditText edPhone;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edRe_Password;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private StorageReference reference = FirebaseStorage.getInstance().getReference("GreenCard");
    private Uri imageUri;
    private Uri profileUri;
    private ImageView imageView;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "EMAIL";
    private ProgressBar progressBar;
    private ProgressBar progressBarUserName;
    private ImageView profile;
    private EditText edUserName;
    private boolean test = false;
    private boolean emailTest = false;
    private String userName;
    private Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        edName = findViewById(R.id.FirstName);
        edLastName = findViewById(R.id.LastName);
        edId = findViewById(R.id.Id);
        edEmail = findViewById(R.id.Email);
        edPassword = findViewById(R.id.Password);
        edRe_Password = findViewById(R.id.Re_Password);
        imageView = findViewById(R.id.imageView3);
        progressBar = findViewById(R.id.progressBar);
        progressBarUserName = findViewById(R.id.progressBar3);
        profile = findViewById(R.id.profile_image);
        edUserName = findViewById(R.id.username_normal);
        progressBar.setVisibility(View.INVISIBLE);
        edPhone = findViewById(R.id.PhoneNumber);
        progressBarUserName.setVisibility(View.INVISIBLE);
    }
//start user account
    public boolean UserAccount(View view) {

        if (!(edUserName.getText().toString().length() > 3 && true)) {
            Toast.makeText(this, "UserName Error", Toast.LENGTH_LONG).show();
            return false;

        } else if (edName.getText().length() > 1 && edEmail.getText().length() < 1) {
            Toast.makeText(this, "First name Error", Toast.LENGTH_LONG).show();
            return false;
        } else if (edLastName.getText().length() < 1) {
            Toast.makeText(this, "the lastName Error", Toast.LENGTH_LONG).show();
            return false;
        } else if (edPassword.getText().length() < 5 && edPassword.getText().toString().equals(edRe_Password.getText().toString()))
        {
            Toast.makeText(this, "Password Error the pass word must to pe up from 5 latter's", Toast.LENGTH_LONG).show();
            return false;
        } else if (edId.getText().length() < 9) {
            Toast.makeText(this, "ID  Error the id word must to pe up from 9 number", Toast.LENGTH_LONG).show();
            return false;
        } else if (edPhone.getText().length() < 10) {
            Toast.makeText(this, "phone  Error the id word must to pe up from 10 number", Toast.LENGTH_LONG).show();
            return false;
        } else if (edEmail.getText().length() < 1 && emailTest) {
            Toast.makeText(this, "Email  Error", Toast.LENGTH_LONG).show();
            return false;
        } else {
            userName = edUserName.getText().toString();
            if (imageUri != null) {
                uploadToFirebase(imageUri, 0);
            } else {
                Toast.makeText(this, "No Green Card", Toast.LENGTH_LONG).show();
                 return false;
            }

            if (profileUri != null) {
                uploadToFirebase(profileUri, 1);
            } else {
                Toast.makeText(this, "No profile photo", Toast.LENGTH_LONG).show();
                return false;
            }
            if(imageUri==null || profileUri== null)
            {
                Toast.makeText(this, "No profile photo", Toast.LENGTH_LONG).show();
                 return false;
            }

            account = new Account(userName, edName.getText().toString(), edLastName.getText().toString(), edPassword.getText().toString(), edEmail.getText().toString(), edPhone.getText().toString(), edPhone.getText().toString(), 1, profileUri.toString(), imageUri.toString());
            myRef.push().setValue(account);


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        Account account=(i.next()).getValue(Account.class);
                        if(account.getUsername()!=null) {
                            if (account.getUsername().equals(userName)) {
                                String ke = i.next().getKey();
                                SaveData(ke);
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
            return true;

        }
    }
    public  void  SaveData(String key)
    {
        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(DATA_TAG, userName);
        editor.putInt("Type", 1);
        editor.commit();
    }

    //upload Green card and profile image
    private void uploadToFirebase(Uri uri, int code) {

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (code == 0)
                            imageUri = uri;
                        if (code == 1)
                            profileUri = uri;
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Create_Account_Activity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        goTOActivity();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);

                Toast.makeText(Create_Account_Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//go to Activity
    private void goTOActivity() {
        Intent intent = new Intent(this, UserMainActivity.class);
        intent.putExtra("Email", edEmail.getText().toString());
        startActivity(intent);
    }
    //go to bulid file
    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }


    public boolean Storage(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 2);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            profileUri = data.getData();
            profile.setImageURI(profileUri);
        }
    }

    public void AddProfilephotoNormal(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 3);
    }

    public Boolean cheekUseName(View view) {
        progressBarUserName.setVisibility(View.VISIBLE);
        userName = edUserName.getText().toString();
        if (userName.length() >= 5) {

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        String s=(i.next()).child("username").getValue(String.class);
                        if (s.equals(userName)){
                            test = false;
                            Toast.makeText(Create_Account_Activity.this, "The name is already exists", Toast.LENGTH_LONG).show();
                            progressBarUserName.setVisibility(View.INVISIBLE);
                            break;
                        }

                    }
                    test = true;
                    Toast.makeText(Create_Account_Activity.this, "OK you can use it", Toast.LENGTH_LONG).show();
                    progressBarUserName.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                    progressBarUserName.setVisibility(View.INVISIBLE);
                }

            });
            //test
        }else Toast.makeText(Create_Account_Activity.this, "Error the name must to be up 5", Toast.LENGTH_LONG).show();
        return true;
    }
}

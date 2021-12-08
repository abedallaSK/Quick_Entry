package com.example.startapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.startapplication.classes.Account;
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

import java.net.URL;
import java.util.Iterator;

public class Create_Account_Activity extends AppCompatActivity {

    private  EditText edNumber;
    private EditText edCode;
    private EditText edName;
    private EditText edLastName;
    private EditText edId;
    private EditText edPhone;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edRe_Password;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private StorageReference reference = FirebaseStorage.getInstance().getReference("GreenCard");
    private Uri imageUriWeb;
    private Uri imageUriData;
    private Uri profileUriWeb;
    private Uri profileUriData;
    private ImageView imageView;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private ProgressBar progressBar;
    private ProgressBar progressBarUserName;
    private ImageView profile;
    private EditText edUserName;
    private boolean test = false;
    private boolean emailTest = false;
    private String userName;
    private Account account;
    private Button btCreate;
    private Button btCheck;
    private   String key;
    private int type;
    private Button btGreen;
    private Switch swIgnore;

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
    };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 0);
        type=getIntent().getIntExtra("Type",0);
        if(type==0)
        {
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
        }
        edName = findViewById(R.id.FirstName);


        edLastName = findViewById(R.id.LastName);
        edId = findViewById(R.id.Id);
        edEmail = findViewById(R.id.Email);
        edPassword = findViewById(R.id.Password);
        edRe_Password = findViewById(R.id.Re_Password);
        imageView = findViewById(R.id.imageView3);
        progressBar = findViewById(R.id.progressBar);
        progressBarUserName = findViewById(R.id.progressBar3);
        profile = findViewById(R.id.imageView_mainProfile);
        edUserName = findViewById(R.id.username_normal);
        progressBar.setVisibility(View.INVISIBLE);
        edPhone = findViewById(R.id.PhoneNumber);
        btCreate=findViewById(R.id.btCreate);
        btCheck=findViewById(R.id.btCheck);
        edNumber=findViewById(R.id.NumberOfPeople);
        btGreen=findViewById(R.id.button6);
        swIgnore=findViewById(R.id.switch2);

        if(type==1){
            edNumber.setVisibility(View.INVISIBLE);
            swIgnore.setVisibility(View.INVISIBLE);
        }
        if(type==2) {
           edUserName.setHint("Company name");
            btGreen.setVisibility(View.INVISIBLE);
            profile.setImageResource(R.drawable.profile_img);

        }
        btCheck.setEnabled(false);
        btCreate.setEnabled(false);
        progressBarUserName.setVisibility(View.INVISIBLE);

        edUserName.addTextChangedListener(loginTextWatcher2);
        edName.addTextChangedListener(loginTextWatcher);
        edLastName.addTextChangedListener(loginTextWatcher);
        edId.addTextChangedListener(loginTextWatcher);
        edEmail.addTextChangedListener(loginTextWatcher);
        edPassword.addTextChangedListener(loginTextWatcher);
        edRe_Password.addTextChangedListener(loginTextWatcher);
        edUserName.addTextChangedListener(loginTextWatcher);
        edPhone.addTextChangedListener(loginTextWatcher);

    }

    private final TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edNameInput = edName.getText().toString().trim();
            String edLastNameInput = edLastName.getText().toString().trim();
            String edIdInput = edId.getText().toString().trim();
            String edEmailInput = edEmail.getText().toString().trim();
            String edPasswordInput = edPassword.getText().toString().trim();
            String edRe_PasswordInput = edRe_Password.getText().toString().trim();
            String edUserNameInput = edUserName.getText().toString().trim();
            edUserName.setTextColor(R.color.black);
            String edPhoneInput = edPhone.getText().toString().trim();

            btCreate.setEnabled(!edNameInput.isEmpty() && !edLastNameInput.isEmpty() && !edIdInput.isEmpty() &&
                    !edEmailInput.isEmpty() && !edPasswordInput.isEmpty() && !edRe_PasswordInput.isEmpty() &&
                    !edUserNameInput.isEmpty() && !edPhoneInput.isEmpty());
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    private final TextWatcher loginTextWatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edUserUserNameInput = edUserName.getText().toString().trim();
                if(edUserUserNameInput.length()>5)
                    btCheck.setEnabled(true);
                else  btCheck.setEnabled(false);
        }
        @SuppressLint("ResourceAsColor")
        @Override
        public void afterTextChanged(Editable s) {
           // edUserName.setTextColor(R.color.black);
        }
    };



//start user account
    public boolean UserAccount(View view) {

        if (!(edUserName.getText().toString().length() > 3 && !test)) {
            Toast.makeText(this, "UserName Error", Toast.LENGTH_LONG).show();
            return false;

        } else if (edName.getText().length() > 1 && edEmail.getText().length() < 1) {
            Toast.makeText(this, "First name Error", Toast.LENGTH_LONG).show();
            return false;
        } else if (edLastName.getText().length() < 1) {
            Toast.makeText(this, "the lastName Error", Toast.LENGTH_LONG).show();
            return false;
        } else if (!(edPassword.getText().toString().equals(edRe_Password.getText().toString()))) {
            Toast.makeText(this, "the password must be equals", Toast.LENGTH_LONG).show();
            if (edPassword.getText().length() < 5) {
                Toast.makeText(this, "the password must be more then 5", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (edId.getText().length() != 9) {
            Toast.makeText(this, "ID  Error the id word must to pe up from 9 number", Toast.LENGTH_LONG).show();
            return false;
        } else if (edPhone.getText().length() != 10) {
            Toast.makeText(this, "phone  Error the id word must to pe up from 10 number", Toast.LENGTH_LONG).show();
            return false;
        } else if (edEmail.getText().length() < 1 && emailTest) {
            Toast.makeText(this, "Email  Error", Toast.LENGTH_LONG).show();
            return false;
        } else {
            userName = edUserName.getText().toString();
            if (type==1) {
                if (imageUriData != null) {
                    uploadToFirebase(imageUriData, 0);
                } else {
                    Toast.makeText(this, "No Green Card", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            if (profileUriData != null) {
                uploadToFirebase(profileUriData, 1);
            } else {
                Toast.makeText(this, "No profile photo", Toast.LENGTH_LONG).show();
                return false;
            }

            return true;
        }
        return false;
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
                            imageUriWeb = uri;
                        if (code == 1)
                            profileUriWeb = uri;
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Create_Account_Activity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        if (type==1){
                        if (profileUriWeb != null && imageUriWeb != null)
                        goTOActivity();}
                        if(type==2)
                        {
                            if (profileUriWeb != null)
                                goTOActivity();
                        }
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

        key = myRef.push().getKey();
        if(type==1) {
            account = new Account(userName, edName.getText().toString(), edLastName.getText().toString(),
                    edPassword.getText().toString(), edEmail.getText().toString(),
                    edPhone.getText().toString(), edId.getText().toString(), 1,
                    profileUriWeb.toString(), imageUriWeb.toString(), key);

            myRef.child(key).setValue(account);
            SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(DATA_TAG, key);
            editor.putInt("Type", type);
            editor.commit();
            startActivity(new Intent(this, UserMainActivity.class));
        }
        if(type==2) {
            String value= edNumber.getText().toString();
            int finalValue=Integer.parseInt(value);
            account = new Account(userName, edName.getText().toString(), edLastName.getText().toString(),
                    edPassword.getText().toString(), edEmail.getText().toString(),
                    edPhone.getText().toString(), edId.getText().toString(), 2,
                    profileUriWeb.toString(), key,finalValue);
            myRef.child(key).setValue(account);
            SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(DATA_TAG, key);
            editor.putInt("Type", type);
            editor.commit();
            startActivity(new Intent(this, BusinessMainActivity.class));
        }

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
            imageUriData = data.getData();
            imageView.setImageURI(imageUriData);
        }
        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            profileUriData = data.getData();
            profile.setImageURI(profileUriData);
        }
    }

    public void AddProfilePhotoNormal(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 3);
    }

    public Boolean CheekUseName(View view) {
        progressBarUserName.setVisibility(View.VISIBLE);
        userName = edUserName.getText().toString();
        test=false;
        if (userName.length() >= 5) {
            myRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        String s=(i.next()).child("username").getValue(String.class);
                        if (s.equals(userName)){
                            test = true;
                            edUserName.setTextColor(R.color.red);
                            progressBarUserName.setVisibility(View.INVISIBLE);
                            break;
                        }

                    }
                    if(!test) {
                        Toast.makeText(Create_Account_Activity.this, "OK you can use it", Toast.LENGTH_LONG).show();
                        btCheck.setText("OK");
                        btCheck.setEnabled(false);
                    } else {
                        Toast.makeText(Create_Account_Activity.this, "The name is already exists", Toast.LENGTH_LONG).show();
                        edUserName.setTextColor(R.color.red);
                    }
                    progressBarUserName.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                    progressBarUserName.setVisibility(View.INVISIBLE);
                }

            });
            //test
        }else Toast.makeText(Create_Account_Activity.this, "Error the name must to be up 5", Toast.LENGTH_LONG).show();
        progressBarUserName.setVisibility(View.INVISIBLE);
        return true;
    }

    public void SendCode(View view) {
        Toast.makeText(this,"coming soon",Toast.LENGTH_SHORT).show();
    }
}

package com.example.startapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.startapplication.classes.Account;
import com.example.startapplication.databinding.ActivityProfileBinding;
import com.example.startapplication.databinding.ActivityUserBinding;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.security.Key;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private StorageReference reference = FirebaseStorage.getInstance().getReference("GreenCard");
    private String key;
    private final Handler mHideHandler = new Handler();
    private boolean isProfilePhoto=true;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";
    private  Account account;

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
    };
    private Uri profileUriData;
    private Uri greenUriData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding =ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 0);

        viewEnabled(false);


        key=getIntent().getStringExtra("KEY");
        if(key!=null) {
            myRef.child(key).addValueEventListener(new ValueEventListener() {
                @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    account = dataSnapshot.getValue(Account.class);
                    if (account != null) {
                        binding.ednameProfile.setText(account.getName());
                        binding.edLastNameProfile.setText(account.getId());
                        Picasso.get().load(account.getProfileUri()).into(binding.imageView17);
                        binding.edEmail.setText(account.getEmail());
                        binding.edPhone.setText(account.getPhoneNumber());
                        binding.edPassword.setText(account.getPassword());
                        binding.edMaxNumber.setText(account.getNumberOfPeople()+"");
                       if(account.getType()==1)  {
                           binding.edNumberOfDoor.setText(account.getDate());
                           Picasso.get().load(account.getGreenUri()).into(binding.imageView10);}
                       else binding.imageView10.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
            binding.ednameProfile.addTextChangedListener(loginTextWatcher);
            binding.edLastNameProfile.addTextChangedListener(loginTextWatcher);
            binding.edEmail.addTextChangedListener(loginTextWatcher);
            binding.edPhone.addTextChangedListener(loginTextWatcher);
           // binding.edBirthday.addTextChangedListener(loginTextWatcher);
            binding.edMaxNumber.addTextChangedListener(loginTextWatcher);
            binding.edNumberOfDoor.addTextChangedListener(loginTextWatcher);
            binding.edPassword.addTextChangedListener(loginTextWatcher);


    }

    private final TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edNameInput = binding.ednameProfile.getText().toString().trim();
            String edLastNameInput =   binding.edLastNameProfile.getText().toString().trim();
            String edIdInput =  binding.edEmail.getText().toString().trim();
            String edEmailInput = binding.edPhone.getText().toString().trim();
            String edPasswordInput =  binding.edPassword.getText().toString().trim();
            String edRe_PasswordInput = binding.edMaxNumber.getText().toString().trim();
            String edUserNameInput =binding.edNumberOfDoor.getText().toString().trim();
          //  String edBi =binding.edBirthday.getText().toString().trim();

            if(binding.btEditProfile.getText().toString().equals("Save"))
            {
                binding.btEditProfile.setEnabled(!edNameInput.isEmpty() && !edLastNameInput.isEmpty() && !edIdInput.isEmpty() &&
                    !edEmailInput.isEmpty() && !edPasswordInput.isEmpty() && !edRe_PasswordInput.isEmpty() &&
                    !edUserNameInput.isEmpty());
            }
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    public void viewEnabled(boolean isEnabled)
    {
        binding.ednameProfile.setEnabled(isEnabled);
        binding.edLastNameProfile.setEnabled(isEnabled);
        binding.edEmail.setEnabled(isEnabled);
        binding.edPhone.setEnabled(isEnabled);
        binding.edPassword.setEnabled(isEnabled);
        binding.edMaxNumber.setEnabled(isEnabled);

    }
    @SuppressLint("SetTextI18n")
    public void edit(View view) {

        if(binding.btEditProfile.getText().toString().equals("Edit"))
        {
           viewEnabled(true);
            binding.btEditProfile.setText("Save");
        }else  if(binding.btEditProfile.getText().toString().equals("Save")){
            binding.btEditProfile.setText("Edit");
            viewEnabled(false);

            myRef.child(key).child("name").setValue(binding.ednameProfile.getText().toString());
            myRef.child(key).child("familyName").setValue(binding.edLastNameProfile.getText().toString());
            myRef.child(key).child("password").setValue(binding.edPassword.getText().toString());
            myRef.child(key).child("email").setValue(binding.edEmail.getText().toString());
            myRef.child(key).child("phoneNumber").setValue(binding.edPhone.getText().toString());
            if(profileUriData!=null) uploadToFirebase(profileUriData, 0);
            if(account.getType()==1)
            {
                    myRef.child(key).child("checkGreen").setValue(0);
                    if(greenUriData!=null)  uploadToFirebase(profileUriData, 1);
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 if(isProfilePhoto) {
                     profileUriData = result.getUri();
                     binding.imageView17.setImageURI(profileUriData);
                 }
                 else{
                     greenUriData=result.getUri();
                         binding.imageView10.setImageURI(greenUriData);
                     }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(ProfileActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                }
        }
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }



    private void uploadToFirebase(Uri uri, int code) {

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (code == 0)
                            myRef.child(key).child("profileUri").setValue(uri.toString());
                        if (code == 1)
                            myRef.child(key).child("greenUri").setValue(uri.toString());
                        //progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ProfileActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        binding.btEditProfile.setText("saved");
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
               // progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                binding.btEditProfile.setText("not saved");
            }
        });
    }
    public void changeProfile(View view) {
        isProfilePhoto=true;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void changeGreen(View view) {
        isProfilePhoto=false;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }



    public void logOut_Profile(View view) {
        SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void back(View view) {
    }
}
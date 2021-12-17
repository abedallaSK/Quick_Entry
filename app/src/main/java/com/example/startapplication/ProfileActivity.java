package com.example.startapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
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


    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
    };
    private Uri profileUriData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding =ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 0);

        binding.tvName.setEnabled(false);
        binding.tvName.setEnabled(false);
        binding.edEmail.setEnabled(false);
        binding.edPhone.setEnabled(false);

        key=getIntent().getStringExtra("KEY");
        if(key!=null) {
            myRef.child(key).addValueEventListener(new ValueEventListener() {
                @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Account account = dataSnapshot.getValue(Account.class);
                    if (account != null) {
                        binding.tvName.setText(account.getName() + " " + account.getFamilyName());
                        binding.tvUsername.setText(account.getId());
                        Picasso.get().load(account.getProfileUri()).into(binding.imageView17);
                        binding.edEmail.setText(account.getEmail());
                        binding.edPhone.setText(account.getPhoneNumber());
                       if(account.getType()==1)
                           Picasso.get().load(account.getGreenUri()).into(binding.imgProfile);

                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
    }

    public void edit(View view) {

        if(binding.edit.getText()=="edit")
        {
            //binding.tvName.setEnabled(true);
            binding.edEmail.setEnabled(true);
            binding.edPhone.setEnabled(true);
            binding.edit.setText("save");

        }else {
            binding.edit.setText("edit");
            //binding.tvName.setEnabled(false);
            binding.edEmail.setEnabled(false);
            binding.edPhone.setEnabled(false);
            //if(binding.tvName.getText().length()>1)
            //{
              //  Toast.makeText(this, "the name error", Toast.LENGTH_SHORT).show();
            /*}else*/ if(binding.edEmail.getText().length()>0){
                Toast.makeText(this, "the email error", Toast.LENGTH_SHORT).show();
            }else if(binding.edPhone.getText().length()>0){
                Toast.makeText(this, "the phone error", Toast.LENGTH_SHORT).show();
            }else {
                myRef.child(key).child("email").setValue(binding.edEmail.getText());
                myRef.child(key).child("id").setValue(binding.edPhone.getText());
            }
       }

    }

    public void save(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                profileUriData = result.getUri();
                binding.imgProfile.setImageURI(profileUriData);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void changeProfile(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

   /* private void uploadToFirebase(Uri uri, int code) {
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (code == 1)
                        //    profileUriWeb = uri;
                      //  progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ProfileActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        //if (type==1){
                          //  if (profileUriWeb != null && imageUriWeb != null)
                           //     goTOActivity();}
                       // if(type==2 || type==3)
                        //{
                           // if (profileUriWeb != null)
                               // goTOActivity();
                        }
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
              //  progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //progressBar.setVisibility(View.INVISIBLE);

                Toast.makeText(ProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }*/
}
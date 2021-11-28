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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Create_Account_Activity extends AppCompatActivity {

    private EditText edName;
    private EditText edLastName;
    private EditText edId;
    private EditText edPhone;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edRe_Password;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
    private StorageReference reference= FirebaseStorage.getInstance().getReference("GreenCard");
    private Uri imageUri;
    private ImageView imageView;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "EMAIL";
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        edName=findViewById(R.id.FirstName);
        edLastName=findViewById(R.id.LastName);
        edId=findViewById(R.id.Id);
        edEmail=findViewById(R.id.Email);
        edPassword=findViewById(R.id.Password);
        edRe_Password=findViewById(R.id.Re_Password);
        imageView=findViewById(R.id.imageView3);
        progressBar=findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);
    }

    public void UserAccount(View view) {

        if(edName.getText().length()>1 && edEmail.getText().length()>1 && edPassword.getText().length()>1 ) {
            String email=edEmail.getText().toString();
            myRef.child(email).child("Name").setValue(edName.getText().toString());
            myRef.child(email).child("Password").setValue(edPassword.getText().toString());
            myRef.child(email).child("Email").setValue(edEmail.getText().toString());
            myRef.child(email).child("Type").setValue(1);

            if(imageUri!=null)
            {
                uploadToFirebase(imageUri);
            }else Toast.makeText(this,"No image",Toast.LENGTH_LONG);


            SharedPreferences mSettings = this.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(DATA_TAG, edEmail.getText().toString());
            editor.putInt("Type",1);
            editor.commit();

        }
        else  Toast.makeText(this,"you Must to full the all ", Toast.LENGTH_SHORT).show();
    }

    private void uploadToFirebase(Uri uri){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(uri.toString());
                        String modelId = myRef.push().getKey();
                        myRef.child(edEmail.getText().toString()).child("Green").setValue(model);
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

    private  void goTOActivity()
    {
        Intent intent = new Intent(this, UserMainActivity.class);
        intent.putExtra("Email", edEmail.getText().toString());
        startActivity(intent);
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


    public void Storage(View view) {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK && data !=null) {
            imageUri=data.getData();
            imageView.setImageURI(imageUri);


        }
    }
}
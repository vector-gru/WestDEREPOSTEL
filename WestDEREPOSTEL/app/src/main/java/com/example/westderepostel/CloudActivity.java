package com.example.westderepostel;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CloudActivity extends AppCompatActivity {

    Button idSelectFileBtn, idUploadBtn, idFetchFilesBtn;
    TextView idNotificationTextView;
    Uri pdfUri; // uri are actually URLs that are meant for local storage

    FirebaseStorage storage;// used for uploading files.. Ex : pdf
    FirebaseDatabase database;// used to store URLs of uploaded files...
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);

        idFetchFilesBtn=findViewById(R.id.idFetchFilesBtn);//object creation successful
        idFetchFilesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(CloudActivity.this,FetchRecyclerViewActivity.class));

            }
        });

        storage=FirebaseStorage.getInstance();//return an object of FireBase Storage
        database=FirebaseDatabase.getInstance();// return an object of Firebase Database

        idSelectFileBtn=findViewById(R.id.idSelectFileBtn);
        idUploadBtn=findViewById(R.id.idUploadBtn);
        idNotificationTextView=findViewById(R.id.idNotificationTextView);

        idSelectFileBtn.setOnClickListener(view -> {

            if (ContextCompat.checkSelfPermission(CloudActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                selectPdf();
            }
            else
                ActivityCompat.requestPermissions(CloudActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);

        });

        idUploadBtn.setOnClickListener(view -> {
            if (pdfUri!=null)// the user has successfully selected the file
            uploadFile(pdfUri);
            else
                Toast.makeText(CloudActivity.this, "Select a File", Toast.LENGTH_LONG).show();

        });


    }

    private void uploadFile(Uri pdfUri) {

        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName=System.currentTimeMillis()+".pdf";
        final String fileName1=System.currentTimeMillis()+"";
        StorageReference storageReference=storage.getReference();//returns root path

        storageReference.child("PdfUploads").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(taskSnapshot -> {

                    String url=taskSnapshot.getStorage().getDownloadUrl().toString(); // return the url of uploaded file...
                    //store the url in realtime database...
                    DatabaseReference reference=database.getReference();// return the path to root

                    reference.child("PdfUploadUrls").child(fileName1).setValue(url).addOnCompleteListener(task -> {

                        if(task.isSuccessful())
                            Toast.makeText(CloudActivity.this,"File is successfully uploaded",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(CloudActivity.this,"File upload failed, check your internet connection please...",Toast.LENGTH_LONG).show();

                    });

                }).addOnFailureListener(e -> Toast.makeText(CloudActivity.this,"File upload failed, check internet connection",Toast.LENGTH_LONG).show()).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                // track the progress of our upload
                int currentProgress= (int) (100*snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
            Toast.makeText(CloudActivity.this,"Please provide permission...", Toast.LENGTH_LONG).show();

    }

    private void selectPdf() {

        // to offer user to select a file using file manager
        //ve will be using an Intent

        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);// to fetch files
        startActivityForResult(intent, 86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //check whether user has selected file or not (ex: pdf)

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {

            pdfUri=data.getData();//return the uri of selected file...
            idNotificationTextView.setText("File selected : "+ data.getData().getLastPathSegment());


        }
        else{
            Toast.makeText(CloudActivity.this, "Please select a file", Toast.LENGTH_LONG).show();
        }
    }
}
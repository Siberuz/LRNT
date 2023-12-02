package com.example.lrnt;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

public class CoursePage extends AppCompatActivity {

    ObjectCourse objectCourse;
    StorageReference sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);
        objectCourse = getIntent().getParcelableExtra("courseObject");
        PDFView pdfV = findViewById(R.id.pdfVw);
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        sr = storage.getReference("course/"+objectCourse.getTitle()+".pdf");
        sr.getStream().addOnSuccessListener(new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(StreamDownloadTask.TaskSnapshot taskSnapshot) {
                pdfV.fromStream(taskSnapshot.getStream()).load();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Fail :"+"as", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
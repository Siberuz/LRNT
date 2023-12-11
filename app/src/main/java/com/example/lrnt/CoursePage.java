package com.example.lrnt;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.util.HashMap;
import java.util.Map;

public class CoursePage extends AppCompatActivity {

    ObjectCourse objectCourse;
    StorageReference sr;
    TextView qz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);
        qz = findViewById(R.id.quiz);
        objectCourse = getIntent().getParcelableExtra("courseObject");
        PDFView pdfV = findViewById(R.id.pdfVw);
        FirebaseStorage storage;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        storage = FirebaseStorage.getInstance();
        sr = storage.getReference("course/"+objectCourse.getTitle()+".pdf");

        qz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference dr = db.collection("users").document(uid).collection("cleared").document(objectCourse.getTitle());
                dr.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()){
                            Toast.makeText(getApplicationContext(),"You already do this quiz!", Toast.LENGTH_SHORT).show();
                        }
                        else if(!value.exists()){
                            Toast.makeText(getApplicationContext(),"Quiz Started!", Toast.LENGTH_SHORT).show();
                            Map<String, Object> user = new HashMap<>();
                            user.put("score", 0);
                            dr.set(user);
                            Intent intent = new Intent(CoursePage.this, Quiz.class);
                            intent.putExtra("courseObject", objectCourse);
                            startActivity(intent);
                        }
                    }
                });

            }
        });
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
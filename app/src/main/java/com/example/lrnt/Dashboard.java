package com.example.lrnt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Dashboard extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView userName_tv;
    StorageReference sr;
    StorageReference sr1;
    private TextView ProfScore;
    int r;
    int r1;
    private Button lo;
    private ImageView pht1;
    private ImageView pht2;
    private TextView suggest;
    private TextView suggest2;
    private CardView c1;
    private CardView c2;
    private static ArrayList<String> courseal;
    private static int counter = 0;
    private static int score = 0;
    private static int total = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dashboard_layout, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        courseal = new ArrayList<>();
        userName_tv = v.findViewById(R.id.ProfileName);
        ProfScore = v.findViewById(R.id.ProfileScore);
        pht1 = v.findViewById(R.id.SuggestionLogo);
        pht2 = v.findViewById(R.id.SuggestionLogo_2);
        lo = v.findViewById(R.id.LogOut);
        c1 = v.findViewById(R.id.CardSuggestion_1);
        c2 = v.findViewById(R.id.CardSuggestion_2);
        suggest = v.findViewById(R.id.SuggestionTitle);
        suggest2 = v.findViewById(R.id.SuggestionTitle_2);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            String uid = currentUser.getUid();

            DocumentReference useRef = db.collection("users").document(uid);

            useRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                   if(documentSnapshot.exists()){
                       String nama = documentSnapshot.getString("name");
                       userName_tv.setText(nama);
                   } else {
                       Toast.makeText(getActivity(), "dokumen pengguna tidak di temukan", Toast.LENGTH_SHORT).show();
                   }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "data pengguna gagal di ambil", Toast.LENGTH_SHORT).show();
                }
            });

            db.collection("users").document(uid).collection("cleared").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        counter = 0;
                        total = 0;
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            score = Integer.valueOf(documentSnapshot.get("score").toString());
                            total = total + score;
                            counter++;
                        }
                        if(counter > 1){
                            total = total/(counter-1);
                            ProfScore.setText(Integer.toString(total));
                        }else{
                            ProfScore.setText("not yet scored");
                            }
                        }
                 }
                });
            }
        COTD();

        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectCourse oc = new ObjectCourse(courseal.get(r), "");
                Intent intent = new Intent(getActivity(), CoursePage.class);
                intent.putExtra("courseObject", oc);
                startActivity(intent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectCourse oc = new ObjectCourse(courseal.get(r1), "");
                Intent intent = new Intent(getActivity(), CoursePage.class);
                intent.putExtra("courseObject", oc);
                startActivity(intent);
            }
        });
        return  v;
    }

    public void COTD(){
        db.collection("course").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        courseal.add(document.get("title").toString());
                    }
                    Random randm = new Random();
                    r = randm.nextInt(courseal.size());
                    String rndm = courseal.get(r);
                    suggest.setText(rndm);
                    r1 = randm.nextInt(courseal.size());
                    String rndm1 = courseal.get(r1);
                    suggest2.setText(rndm1);
                    sr = FirebaseStorage.getInstance().getReference("course/"+courseal.get(r)+".png");
                    sr1 = FirebaseStorage.getInstance().getReference("course/"+courseal.get(r1)+".png");
                    try {
                        File local = File.createTempFile("tempfile",".jpg");
                        sr.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(local.getAbsolutePath());
                                pht1.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                        sr1.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(local.getAbsolutePath());
                                pht2.setImageBitmap(bitmap);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
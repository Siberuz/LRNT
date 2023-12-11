package com.example.lrnt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Random;

public class Dashboard extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView userName_tv;
    private TextView ProfScore;
    private TextView suggest;
    private TextView suggest2;
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
                        total = total/(counter-1);
                        ProfScore.setText(Integer.toString(total));
                        }
                 }
                });
            }
        COTD();
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
                    String rndm = courseal.get(randm.nextInt(courseal.size()));
                    suggest.setText(rndm);
                    Random randm1 = new Random();
                    String rndm1 = courseal.get(randm1.nextInt(courseal.size()));
                    suggest2.setText(rndm1);
                }
            }
        });
    }
}
package com.example.lrnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LeaderboardCourse extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<Leaderboard_Score> ls;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AdapterLeaderboardCourse adapterLeaderboardCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_course);
        String title = getIntent().getStringExtra("title");
        ls = new ArrayList<>();
        adapterLeaderboardCourse = new AdapterLeaderboardCourse(LeaderboardCourse.this, ls);
        databind(title);
        rv = findViewById(R.id.Leaderboard_id);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapterLeaderboardCourse);
    }

    public void databind(String title){
        ArrayList<Leaderboard_Score> lsi = new ArrayList<>();
        db.collection("course").document(title).collection("rank")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot ds : task.getResult()){
                                lsi.add(new Leaderboard_Score(ds.getId(), Integer.valueOf(ds.get("score").toString()),0));
                            }
                            int temp = 0;
                            int j = 0;
                            Leaderboard_Score tempO = null;
                            Leaderboard_Score temp1 = null;
                            int tempscore = 0;
                            for(int i = 0; i<lsi.size(); i++){
                                tempscore = 0;
                                for (j = i; j<lsi.size(); j++){
                                    if (tempscore < lsi.get(j).getScore()) {
                                        temp = j;
                                        tempO = lsi.get(j);
                                        tempscore = lsi.get(j).getScore();
                                    }
                                }
                                tempO.setPos(i+1);
                                ls.add(tempO);
                                temp1 = lsi.get(i);
                                lsi.set(temp, temp1);
                                lsi.set(i,tempO);
                            }
                            adapterLeaderboardCourse.notifyDataSetChanged();
                        }
                    }
                });
    }
}
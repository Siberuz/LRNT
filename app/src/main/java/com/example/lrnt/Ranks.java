package com.example.lrnt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Ranks extends Fragment implements SelectRankListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView ranksRecycler;
    List<Ranks_Item> item;
    Ranks_Adapter ranksAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.ranks_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        item = new ArrayList<>();
        dataBind();
        ranksRecycler = view.findViewById(R.id.ranks_recycler);
        ranksRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ranksAdapter = new Ranks_Adapter(getContext(),item, this);
        ranksRecycler.setAdapter(ranksAdapter);

    }

    public void dataBind(){
        db.collection("course").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        item.add(new Ranks_Item(document.get("title").toString()));
                        ranksAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void onItemClicked(Ranks_Item ranks_item) {
        Intent intent = new Intent(getContext(), LeaderboardCourse.class);
        startActivity(intent);
    }
}
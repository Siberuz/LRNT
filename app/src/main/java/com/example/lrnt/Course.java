package com.example.lrnt;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Course extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ObjectCourse> courseal;
    RecyclerView rv;
    AdapterCourse ac;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.course_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseal = new ArrayList<>();
        ac = new AdapterCourse(getContext(), courseal);
        dataBind();
        rv = view.findViewById(R.id.rv);
        Toast.makeText(getContext(),"aaa",Toast.LENGTH_SHORT);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(ac);
    }

    public void dataBind(){
        db.collection("course").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        courseal.add(new ObjectCourse(document.get("title").toString(), document.get("desc").toString()));
                        ac.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
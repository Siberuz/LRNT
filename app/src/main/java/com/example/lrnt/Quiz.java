package com.example.lrnt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {
    TextView question;
    RadioButton first;
    RadioButton second;
    RadioButton third, fourth;
    Button next;
    ArrayList<Question> qst;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int total;
    ObjectCourse objectCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        objectCourse = getIntent().getParcelableExtra("courseObject");
        first = findViewById(R.id.firstChoice);
        second = findViewById(R.id.secondChoice);
        third = findViewById(R.id.thirdChoice);
        fourth = findViewById(R.id.fourthChoice);
        question = findViewById(R.id.qst);
        qst = new ArrayList<Question>();
        next = findViewById(R.id.next);
        int num = 1;
        OperateSoal(1);
    }

    public void OperateSoal(int num){
        String nowq = "Question" + Integer.toString(num);
        Toast.makeText(getApplicationContext(),nowq,Toast.LENGTH_SHORT).show();
        DocumentReference dr = db.collection("course").document("1").collection("Quiz").document(nowq);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot ds = task.getResult();
                    qst.add(new Question(ds.get("answer").toString(), ds.get("c1").toString(), ds.get("c2").toString(), ds.get("c3").toString(), ds.get("c4").toString(), ds.get("question").toString()));
                    question.setText(qst.get(0).getAnswer());

                }
            }
        });
    }
}
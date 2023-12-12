package com.example.lrnt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Quiz extends AppCompatActivity {
    TextView question;
    RadioButton first;
    RadioButton second;
    TextView ans;
    RadioButton third, fourth;
    Button next;
    ArrayList<Question> qst;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int total;
    ObjectCourse objectCourse;
    TextView max;
    TextView courseNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        objectCourse = getIntent().getParcelableExtra("courseObject");
        first = findViewById(R.id.firstChoice);
        ans = findViewById(R.id.ans);
        max = findViewById(R.id.max);
        second = findViewById(R.id.secondChoice);
        third = findViewById(R.id.thirdChoice);
        fourth = findViewById(R.id.fourthChoice);
        question = findViewById(R.id.qst);
        qst = new ArrayList<Question>();
        next = findViewById(R.id.next);
        courseNameTV = findViewById(R.id.courseName);

        courseNameTV.setText(objectCourse.getTitle());

        OperateSoal(1, 0);
    }

    public void OperateSoal(int numb, int tcount){
        String nowq = "Question" + Integer.toString(numb);
        DocumentReference dr = db.collection("course").document(objectCourse.getTitle()).collection("Quiz").document(nowq);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot ds = task.getResult();
                    qst.add(new Question(ds.get("answer").toString(), ds.get("c1").toString(), ds.get("c2").toString(), ds.get("c3").toString(), ds.get("c4").toString(), ds.get("question").toString()));
                    ans.setText(qst.get(numb-1).getAnswer());
                    question.setText(qst.get(numb - 1).getQuestion());
                    first.setText(qst.get(numb - 1).getC1());
                    second.setText(qst.get(numb - 1).getC2());
                    third.setText(qst.get(numb - 1).getC3());
                    fourth.setText(qst.get(numb - 1).getC4());
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int num = numb;
                            int Tcount = tcount;
                            if(first.isChecked()){
                                if(first.getText().equals(ans.getText())){
                                    Tcount = Tcount + 1;
                                    first.setChecked(false);
                                }
                                num = num +1;
                            } else if (second.isChecked()) {
                                if(second.getText().equals(ans.getText())){
                                    Tcount = Tcount + 1;
                                    second.setChecked(false);
                                }
                                num = num +1;
                            } else if (third.isChecked()) {
                                if(third.getText().equals(ans.getText())){
                                    Tcount = Tcount + 1;
                                    third.setChecked(false);
                                }
                                num = num +1;

                            } else if (fourth.isChecked()) {
                                if(fourth.getText().equals(ans.getText())){
                                    Tcount = Tcount + 1;
                                    fourth.setChecked(false);
                                }
                                num = num +1;
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Input Choice", Toast.LENGTH_SHORT).show();
                            }
                            if(num > Integer.valueOf(ds.get("max").toString())){
                                int total = (100/(num -1))*Tcount;
                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String uid = currentUser.getUid();
                                DocumentReference useRef = db.collection("users").document(uid);
                                useRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot.exists()){
                                            DocumentReference documentReference = db.collection("course").document(objectCourse.getTitle()).collection("rank").document(documentSnapshot.get("name").toString());
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("score", total);
                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    DocumentReference dr = db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("cleared").document(objectCourse.getTitle());
                                                    dr.update("score", total);
                                                    Toast.makeText(getApplicationContext(),Integer.toString(total), Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Quiz.this, MainActivity.class);
                                                    intent.putExtra("UID", mAuth.getCurrentUser());
                                                    startActivity(intent);
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getApplicationContext(), "dokumen pengguna tidak di temukan", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                OperateSoal(num,Tcount);
                            }
                        }
                    });
                }
                else{

                }
            }
        });
    }
}
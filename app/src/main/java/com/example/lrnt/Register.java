package com.example.lrnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        mAuth = FirebaseAuth.getInstance();
        EditText name = findViewById(R.id.ET_name);
        EditText email = findViewById(R.id.ET_email);
        EditText password = findViewById(R.id.ET_password);
        Button btn = findViewById(R.id.Btn_regis);
        Button btn_login = findViewById(R.id.registPage_loginButton);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btn_login){
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString() == "" || email.getText().toString() == "" || password.getText().toString() == ""){
                    Toast.makeText(Register.this, "Please Fill all Field!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(email.getText().toString().contains(".") && email.getText().toString().contains("@")){
                        regis(email.getText().toString(), password.getText().toString(), name.getText().toString());
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Email Unvalid!!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }


            private void regis(String email, String password, String name) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String userID = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("email", email);
                                    user.put("name", name);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            DocumentReference documentreference = db.collection("users").document(userID).collection("cleared").document("initialize");
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("score", 0);
                                            documentreference.set(user);
                                            Toast.makeText(getApplicationContext(),"Account Has been Made",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Register.this, Login.class);
                                            startActivity(intent);
                                        }
                                    });
                                }else{
                                    Toast.makeText(getApplicationContext(),"Failed to make Account",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
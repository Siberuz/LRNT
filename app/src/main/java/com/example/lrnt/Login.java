package com.example.lrnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText emailET;
    EditText passwordET;
    Button loginButton;
    FirebaseAuth mAuth;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogIn();
        setContentView(R.layout.login_activity);
        emailET = findViewById(R.id.login_email);
        passwordET = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.loginPage_registerButton);
        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    private void isLogIn() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(Login.this, MainActivity.class);
            Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onClick(View v) {
        if (v == loginButton){
            if(passwordET.getText().toString().equals("") || emailET.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Fill the Field!!!",Toast.LENGTH_SHORT).show();
            }else{
                if(emailET.getText().toString().contains(".") && emailET.getText().toString().contains("@")){
                    mAuth.signInWithEmailAndPassword(emailET.getText().toString().trim(),passwordET.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                Toast.makeText(getApplicationContext(),"Login Berhasil!",Toast.LENGTH_SHORT).show();
                                String UserID = mAuth.getCurrentUser().getUid();
                                intent.putExtra("UID", UserID);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Error!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Email tidak valid!!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }//logic login
            if(v == registerButton){
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                //pindah ke register page
            }
        }

    }

package com.example.lrnt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText emailET;
    EditText passwordET;
    Button loginButton;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        emailET = findViewById(R.id.login_email);
        passwordET = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.loginPage_registerButton);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton){
            //logic login
        }
        if(v == registerButton){
            //pindah ke register page
        }
    }
}
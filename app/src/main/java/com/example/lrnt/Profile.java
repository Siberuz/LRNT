package com.example.lrnt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class Profile extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView tv_name;
    private TextView tv_email;
    private EditText new_password;
    private ImageButton edit_password;
    private ImageButton edit_password_done;
    private EditText old_password;
    private TextView username_tv;
    private EditText username_et;
    private ImageButton edit_username;
    private ImageButton edit_username_done;
    private TextView password_tv;
    private String uid;
    private FirebaseUser currentUser;
    private  DocumentReference useRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.profile_layout, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tv_name = v.findViewById(R.id.profileName);
        tv_email = v.findViewById(R.id.Email_profile_id);
        edit_password = v.findViewById(R.id.edit_password_button);
        old_password = v.findViewById(R.id.profile_oldPassword_et);
        new_password = v.findViewById(R.id.profile_newPassword_et);
        edit_password = v.findViewById(R.id.edit_password_button);
        edit_password.setOnClickListener(this);
        edit_password_done = v.findViewById(R.id.edit_password_button_done);
        edit_password_done.setOnClickListener(this);
        password_tv = v.findViewById(R.id.password_tv);

        username_tv = v.findViewById(R.id.username_profile_id);
        username_et = v.findViewById(R.id.profile_username_et);
        edit_username = v.findViewById(R.id.edit_username_button);
        edit_username.setOnClickListener(this);
        edit_username_done = v.findViewById(R.id.edit_username_button_done);
        edit_username_done.setOnClickListener(this);

      currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            uid = currentUser.getUid();

            useRef = db.collection("users").document(uid);

            useRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        String nama = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");

                        tv_name.setText(nama);
                        tv_email.setText(email);
                        username_tv.setText(nama);

                    }else{
                        Toast.makeText(getActivity(), "dokumen tidak ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "data pengguna gagal di ambil", Toast.LENGTH_SHORT).show();
                }
            });

        }
        return v;
    }

    public void updateUsername(String newUsername){
        uid = currentUser.getUid();
        useRef = db.collection("users").document(uid);
        useRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    useRef.update("name", newUsername).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "Username updated Successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed to update username", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "data pengguna gagal di ambil", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updatePassword(String newPassword, String oldPassword){

        if (newPassword.length() < 8) {
            Toast.makeText(getActivity(), "New Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return; 
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> reauthenticationTask) {
                if (reauthenticationTask.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> updateTask) {
                            if (updateTask.isSuccessful()) {
                                Toast.makeText(getActivity(), "Password Successfully Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Password Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Old Password Incorrect. Password Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v == edit_password){
            password_tv.setVisibility(View.GONE);
            old_password.setVisibility(View.VISIBLE);
            new_password.setVisibility(View.VISIBLE);
            edit_password.setVisibility(View.GONE);
            edit_password_done.setVisibility(View.VISIBLE);
        }
        if(v == edit_password_done){
            password_tv.setVisibility(View.VISIBLE);
            old_password.setVisibility(View.GONE);
            new_password.setVisibility(View.GONE);
            edit_password.setVisibility(View.VISIBLE);
            edit_password_done.setVisibility(View.GONE);
            String newPassword = new_password.getText().toString();
            String oldPassword = old_password.getText().toString();
            updatePassword(newPassword, oldPassword);
        }
        if(v == edit_username){
            username_tv.setVisibility(View.GONE);
            username_et.setVisibility(View.VISIBLE);
            edit_username.setVisibility(View.GONE);
            edit_username_done.setVisibility(View.VISIBLE);
            username_et.setText(username_tv.getText().toString());
        }
        if(v == edit_username_done){
            String newUsername = username_et.getText().toString();
            username_tv.setText(newUsername);
            username_tv.setVisibility(View.VISIBLE);
            username_et.setVisibility(View.GONE);
            edit_username.setVisibility(View.VISIBLE);
            edit_username_done.setVisibility(View.GONE);
            updateUsername(newUsername);
        }
    }
}
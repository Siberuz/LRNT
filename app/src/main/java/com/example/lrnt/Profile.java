package com.example.lrnt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Profile extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView tv_name;
    private TextView tv_email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.profile_layout, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tv_name = v.findViewById(R.id.profileName);
        tv_email = v.findViewById(R.id.Email_profile_id);

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            String uid = currentUser.getUid();

            DocumentReference useRef = db.collection("users").document(uid);

            useRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        String nama = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");

                        tv_name.setText(nama);
                        tv_email.setText(email);

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
}
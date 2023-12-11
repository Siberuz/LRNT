package com.example.lrnt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Ranks_Adapter extends RecyclerView.Adapter<Ranks_Holder>{

    Context context;
    List<Ranks_Item> items;
    StorageReference sr;
    SelectRankListener listener;

    public Ranks_Adapter(Context context, List<Ranks_Item> items, SelectRankListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Ranks_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Ranks_Holder(LayoutInflater.from(context).inflate(R.layout.card_leaderboard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Ranks_Holder holder, int position) {
        Ranks_Item item = items.get(position);
        holder.ranksText.setText(item.getTitle());
        sr = FirebaseStorage.getInstance().getReference("course/"+item.getTitle()+".png");
        try {
            File local = File.createTempFile("tempfile",".jpg");
            sr.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(local.getAbsolutePath());
                    holder.ranksLogo.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"Failed load Logo", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

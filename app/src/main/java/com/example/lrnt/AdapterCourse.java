package com.example.lrnt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.ViewHolder> {

    Context context;
    ArrayList<ObjectCourse> courseAL;
    SelectCourseListener listener;
    StorageReference sr;

    public AdapterCourse(Context context, ArrayList<ObjectCourse> courseAL, SelectCourseListener listener) {
        this.context = context;
        this.courseAL = courseAL;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterCourse.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_course, parent, false);
        return new AdapterCourse.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCourse.ViewHolder holder, int position) {
        ObjectCourse oc = courseAL.get(position);
        holder.title.setText(oc.getTitle());
        holder.content.setText(oc.getDesc());
        sr = FirebaseStorage.getInstance().getReference("course/"+oc.getTitle()+".png");
        try {
            File local = File.createTempFile("tempfile",".jpg");
            sr.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(local.getAbsolutePath());
                    holder.crsLog.setImageBitmap(bitmap);
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
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(courseAL.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseAL.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView content;
        CardView cd;
        ImageView crsLog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.CourseTitle);
            content = itemView.findViewById(R.id.content);
            cd = itemView.findViewById(R.id.CardCourse);
            crsLog = itemView.findViewById(R.id.CourseLogo);
        }
    }
}

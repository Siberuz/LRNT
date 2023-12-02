package com.example.lrnt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.ViewHolder> {

    Context context;
    ArrayList<ObjectCourse> courseAL;
    SelectCourseListener listener;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.CourseTitle);
            content = itemView.findViewById(R.id.content);
            cd = itemView.findViewById(R.id.CardCourse);
        }
    }
}

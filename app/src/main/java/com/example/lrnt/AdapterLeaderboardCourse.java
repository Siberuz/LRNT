package com.example.lrnt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterLeaderboardCourse extends RecyclerView.Adapter<AdapterLeaderboardCourse.ViewHolder> {
    Context context;
    ArrayList<Leaderboard_Score> al;


    public AdapterLeaderboardCourse(Context context, ArrayList<Leaderboard_Score> al) {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public AdapterLeaderboardCourse.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.leaderboard_item, parent, false);
        return new AdapterLeaderboardCourse.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLeaderboardCourse.ViewHolder holder, int position) {
        Leaderboard_Score ls = al.get(position);
        holder.participant.setText(ls.getName());
        holder.Score.setText(Integer.toString(ls.getScore()));
        holder.rank.setText(Integer.toString(ls.getPos()));
    }

    @Override
    public int getItemCount() {
        return al.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView participant;
        TextView Score;
        TextView rank;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            participant = itemView.findViewById(R.id.Participant_id);
            Score = itemView.findViewById(R.id.Point_id);
            rank = itemView.findViewById(R.id.Rank_participant_id);
            }
        }

    }


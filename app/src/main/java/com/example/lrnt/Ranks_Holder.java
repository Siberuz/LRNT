package com.example.lrnt;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Ranks_Holder extends RecyclerView.ViewHolder {
    ImageView ranksLogo;
    TextView ranksText;
    CardView card;

    public Ranks_Holder(@NonNull View itemView) {
        super(itemView);
        ranksLogo = itemView.findViewById(R.id.LeaderboardLogo);
        ranksText = itemView.findViewById(R.id.LeaderboardTitle);
        card = itemView.findViewById(R.id.CardLeaderboard);
    }
}

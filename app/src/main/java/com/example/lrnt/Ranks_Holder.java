package com.example.lrnt;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Ranks_Holder extends RecyclerView.ViewHolder {
    ImageView ranksLogo;
    TextView ranksText;

    public Ranks_Holder(@NonNull View itemView) {
        super(itemView);
        ranksLogo = itemView.findViewById(R.id.LeaderboardLogo);
        ranksText = itemView.findViewById(R.id.LeaderboardTitle);
    }
}

package com.example.lrnt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Ranks_Adapter extends RecyclerView.Adapter<Ranks_Holder>{

    Context context;
    List<Ranks_Item> items;

    public Ranks_Adapter(Context context, List<Ranks_Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Ranks_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Ranks_Holder(LayoutInflater.from(context).inflate(R.layout.card_leaderboard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Ranks_Holder holder, int position) {
        holder.ranksText.setText(items.get(position).getTitle());
        holder.ranksLogo.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

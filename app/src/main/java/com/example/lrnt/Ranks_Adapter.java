package com.example.lrnt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Ranks_Adapter extends RecyclerView.Adapter<Ranks_Holder>{

    Context context;
    List<Ranks_Item> items;
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

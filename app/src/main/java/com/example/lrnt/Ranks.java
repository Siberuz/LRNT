package com.example.lrnt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Ranks extends Fragment {
    RecyclerView ranksRecycler;
    List<Ranks_Item> item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.ranks_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();

        ranksRecycler = view.findViewById(R.id.ranks_recycler);
        ranksRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Ranks_Adapter ranksAdapter = (new Ranks_Adapter(getContext(),item));
        ranksRecycler.setAdapter(ranksAdapter);

    }

    private void dataInitialize() {
        item = new ArrayList<Ranks_Item>();
        item.add(new Ranks_Item("Introduction to LRNT.", R.drawable.lrnt_logo));
        item.add(new Ranks_Item("Introduction to Test", R.drawable.carbon_next_outline));
    }
}
package com.example.yiming.hotelmanagment.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.util.adapter.RoomFragmentAdapter;

public class RoomFragment extends Fragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recyclerview_layout,container,false);
        recyclerView=v.findViewById(R.id.recyclerView);
        RoomFragmentAdapter roomFragmentAdapter=new RoomFragmentAdapter(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(roomFragmentAdapter);
        return v;
    }
}

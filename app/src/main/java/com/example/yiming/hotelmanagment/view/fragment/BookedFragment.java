package com.example.yiming.hotelmanagment.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.data.livedata.RoomDatabase.RoomViewModel;
import com.example.yiming.hotelmanagment.data.livedata.module.RoomTrans;
import com.example.yiming.hotelmanagment.util.DefaultItemTouchHelpCallback;
import com.example.yiming.hotelmanagment.util.ListViewDecoration;
import com.example.yiming.hotelmanagment.util.adapter.BookFragmentAdapter;

import java.util.Collections;
import java.util.List;

public class BookedFragment extends Fragment {
    RecyclerView recyclerView;
    BookFragmentAdapter bookFragmentAdapter;
    SwipeRefreshLayout mySwipeRefreshLayout;
    RoomViewModel roomViewModel;
    List<RoomTrans> tempList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.booked,container,false);
        recyclerView=v.findViewById(R.id.booked);
        bookFragmentAdapter=new BookFragmentAdapter(getActivity());
        roomViewModel= ViewModelProviders.of(this).get(RoomViewModel.class);
        roomViewModel.getAllRoomTrans().observe(this, new Observer<List<RoomTrans>>() {
            @Override
            public void onChanged(@Nullable List<RoomTrans> roomTrans) {
                bookFragmentAdapter.setList(roomTrans);
                tempList=roomTrans;
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bookFragmentAdapter);
        mySwipeRefreshLayout=v.findViewById(R.id.mySwipeRefreshLayout);
        // 设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        mySwipeRefreshLayout.setProgressViewOffset(true, 50, 200);

        // 设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        mySwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mySwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        /*
         * 设置手势下拉刷新的监听
         */
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // 刷新动画开始后回调到此方法
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                roomViewModel.checkOut(tempList.get(adapterPosition).getTransactionId());
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                Collections.swap(tempList,srcPosition,targetPosition);
                bookFragmentAdapter.notifyItemMoved(srcPosition,targetPosition);
                return true;
            }
        }));
//        recyclerView.addItemDecoration(new ListViewDecoration(getResources(),R.color.colorAccent));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return v;
    }
}

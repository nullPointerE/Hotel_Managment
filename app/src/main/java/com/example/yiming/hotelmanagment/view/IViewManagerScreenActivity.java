package com.example.yiming.hotelmanagment.view;

import com.example.yiming.hotelmanagment.util.adapter.ManagerScreenAdapter;

public interface IViewManagerScreenActivity {
   public void populateEmployees();

   public void setRecyclerViewAdapter(ManagerScreenAdapter managerScreenAdapter);
}

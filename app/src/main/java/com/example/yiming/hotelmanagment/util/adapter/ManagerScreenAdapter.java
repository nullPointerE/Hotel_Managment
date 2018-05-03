package com.example.yiming.hotelmanagment.util.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Employee;

import java.util.List;

public class ManagerScreenAdapter extends RecyclerView.Adapter<ManagerScreenAdapter.MyViewHolder>{

    private Employee employees;
    private Context context;

    public ManagerScreenAdapter(Employee employees, Context context) {
        this.employees = employees;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_recycler_item, parent, false  );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.employeeName.setText(employees.getRoomdetails().get(position).getEmpname());
        holder.employeePay.setText("$"+employees.getRoomdetails().get(position).getEmpsalary());
        holder.employeeImage.setImageResource(Constants.EMPLOYEEPIC[position%3]);
    }

    @Override
    public int getItemCount() {
        return employees.getRoomdetails().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView employeeImage;
        TextView employeeName, employeePay;
        public MyViewHolder(View itemView) {
            super(itemView);
            employeeImage = itemView.findViewById(R.id.employeeImage);
            employeeName = itemView.findViewById(R.id.employee_name);
            employeePay = itemView.findViewById(R.id.employee_pay);
        }
    }
}
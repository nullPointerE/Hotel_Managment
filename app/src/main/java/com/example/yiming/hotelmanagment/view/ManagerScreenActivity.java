package com.example.yiming.hotelmanagment.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toolbar;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Employee;
import com.example.yiming.hotelmanagment.data.remote.EmployeeAPIService;
import com.example.yiming.hotelmanagment.data.remote.EmployeeRetrofitInstance;
import com.example.yiming.hotelmanagment.util.adapter.ManagerScreenAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ManagerScreenActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView employeeRecyclerView;
    private Retrofit retrofit;
    private ManagerScreenAdapter managerScreenAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_screen);
        toolbar = findViewById(R.id.manager_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        employeeRecyclerView = findViewById(R.id.manager_employee_recyclerView);
        employeeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        employeeRecyclerView.setLayoutManager(linearLayoutManager);
        retrofit = EmployeeRetrofitInstance.getEmployeeRetrofitInstance();
        retrofit.create(EmployeeAPIService.class).getEmployees()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);
    }

    private void handleResult(List<Employee> employees) {
        managerScreenAdapter = new ManagerScreenAdapter(employees, this);
        employeeRecyclerView.setAdapter(managerScreenAdapter);
    }
    private void handleError(Throwable throwable) {
        Log.i("handleResult ", "error");
    }
}
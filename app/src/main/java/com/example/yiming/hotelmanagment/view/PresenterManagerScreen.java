package com.example.yiming.hotelmanagment.view;

import android.content.Context;
import android.util.Log;

import com.example.yiming.hotelmanagment.common.Employee;
import com.example.yiming.hotelmanagment.data.remote.EmployeeAPIService;
import com.example.yiming.hotelmanagment.data.remote.EmployeeRetrofitInstance;
import com.example.yiming.hotelmanagment.util.adapter.ManagerScreenAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PresenterManagerScreen implements IPresenterManagerScreen{
    private IViewManagerScreenActivity managerScreenActivity;
    private Retrofit retrofit;
    private Employee employee;
    private ManagerScreenAdapter managerScreenAdapter;
    private Context context;
    public PresenterManagerScreen(Context context, ManagerScreenActivity managerScreenActivity) {
        this.managerScreenActivity = managerScreenActivity;
        this.context = context;
    }

    @Override
    public void jasonCall() {
        EmployeeAPIService apiService = EmployeeRetrofitInstance.getEmployeeRetrofitInstance().create(EmployeeAPIService.class);

        Call<Employee> employeeCall = apiService.getEmployees();

        employeeCall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                employee = response.body();
                Log.i("Retrofit response", response.raw().request().url()+"");
                ManagerScreenAdapter managerScreenAdapter = new ManagerScreenAdapter(employee, context);
                managerScreenActivity.setRecyclerViewAdapter(managerScreenAdapter);
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.i("Response", t.getMessage());
            }
        });
    }
}

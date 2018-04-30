package com.example.yiming.hotelmanagment.view;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.common.AddEmployee;
import com.example.yiming.hotelmanagment.common.Employee;
import com.example.yiming.hotelmanagment.data.remote.EmployeeAPIService;
import com.example.yiming.hotelmanagment.data.remote.EmployeeRetrofitInstance;
import com.example.yiming.hotelmanagment.util.adapter.ManagerScreenAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PresenterAddEmployee implements IPresenterAddEmployee{

    private IViewAddEmployee addEmployee;
    private Retrofit retrofit;
    private Context context;
    private AddEmployee addEmployeeModel;
    public PresenterAddEmployee(Context context, AddEmployeeActivity addEmployee, AddEmployee addEmployeeModel) {
        this.addEmployeeModel = addEmployeeModel;
        this.addEmployee = addEmployee;
        this.context = context;
    }

    @Override
    public void jasonCall() {

        EmployeeAPIService apiService = EmployeeRetrofitInstance.getAddEmployeeRetrofitInstance().create(EmployeeAPIService.class);

        Call<AddEmployee> addEmployee = apiService.getAddEmployees(addEmployeeModel.getEmpName(), addEmployeeModel.getEmpAdd(), addEmployeeModel.getEmpPhone(), addEmployeeModel.getEmpSalary(), addEmployeeModel.getEmpDesign());

       addEmployee.enqueue(new Callback<AddEmployee>() {
           @Override
           public void onResponse(Call<AddEmployee> call, Response<AddEmployee> response) {
               Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onFailure(Call<AddEmployee> call, Throwable t) {
            Log.i("Response Error", t.getMessage());
           }
       });


    }
}

package com.example.yiming.hotelmanagment.view;

import android.content.Context;
import android.util.Log;

import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Employee;
import com.example.yiming.hotelmanagment.data.remote.EmployeeAPIService;
import com.example.yiming.hotelmanagment.data.remote.EmployeeRetrofitInstance;
import com.example.yiming.hotelmanagment.util.adapter.ManagerScreenAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
//        EmployeeAPIService apiService = EmployeeRetrofitInstance.getEmployeeRetrofitInstance().create(EmployeeAPIService.class);
//
//        Call<Employee> employeeCall = apiService.getEmployees();
//
//        employeeCall.enqueue(new Callback<Employee>() {
//            @Override
//            public void onResponse(Call<Employee> call, Response<Employee> response) {
//                employee = response.body();
//                Log.i("Retrofit response", response.raw().request().url()+"");
//                ManagerScreenAdapter managerScreenAdapter = new ManagerScreenAdapter(employee, context);
//                managerScreenActivity.setRecyclerViewAdapter(managerScreenAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<Employee> call, Throwable t) {
//                Log.i("Response", t.getMessage());
//            }
//        });
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.EPLOYEE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(EmployeeAPIService.class).getEmployees().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);


    }

    private void handleResults(Employee employee) {
                ManagerScreenAdapter managerScreenAdapter = new ManagerScreenAdapter(employee, context);
                managerScreenActivity.setRecyclerViewAdapter(managerScreenAdapter);
    }

    private void handleError(Throwable throwable) {
        Log.i("Error Response", throwable.getMessage());

    }
}

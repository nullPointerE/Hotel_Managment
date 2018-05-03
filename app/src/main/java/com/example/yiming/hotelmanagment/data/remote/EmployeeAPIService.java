package com.example.yiming.hotelmanagment.data.remote;

import com.example.yiming.hotelmanagment.common.AddEmployee;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Employee;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EmployeeAPIService {

    @GET(Constants.EMPLOYEE_END_URL)
    Observable<Employee> getEmployees();

    @GET(Constants.ADD_EMPLOYEE_END_URL)
    Call<AddEmployee> getAddEmployees(@Query("empName") String empName, @Query("empAdd") String empAdd, @Query("empPhone") String empPhone, @Query("empSalary") String empSalary, @Query("empDesign") String empDesign );
}

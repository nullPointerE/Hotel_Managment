package com.example.yiming.hotelmanagment.data.remote;

import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Employee;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeAPIService {

    @GET(Constants.EMPLOYEE_END_URL)
    Observable<List<Employee>> getEmployees();
}

package com.example.yiming.hotelmanagment.data.remote;

import com.example.yiming.hotelmanagment.common.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeRetrofitInstance {
    private static Retrofit retrofit;

    public static Retrofit getEmployeeRetrofitInstance(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.EPLOYEE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

    public static Retrofit getAddEmployeeRetrofitInstance(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.ADD_EMPLOYEE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }
}
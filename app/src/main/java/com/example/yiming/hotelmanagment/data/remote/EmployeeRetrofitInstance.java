package com.example.yiming.hotelmanagment.data.remote;

import com.example.yiming.hotelmanagment.common.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeRetrofitInstance {
    private static Retrofit retrofit;

    public static Retrofit getEmployeeRetrofitInstance(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.EPLOYEE_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory( GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
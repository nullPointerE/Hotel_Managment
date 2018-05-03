package com.example.yiming.hotelmanagment;

import android.app.Activity;
import android.content.Context;

import com.example.yiming.hotelmanagment.common.Employee;
import com.example.yiming.hotelmanagment.data.remote.EmployeeAPIService;
import com.example.yiming.hotelmanagment.data.remote.EmployeeRetrofitInstance;
import com.example.yiming.hotelmanagment.util.adapter.ManagerScreenAdapter;
import com.example.yiming.hotelmanagment.view.IViewManagerScreenActivity;
import com.example.yiming.hotelmanagment.view.PresenterManagerScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MVPTest {
    @Mock
    private PresenterManagerScreen presenterManagerScreen;

    @Mock
    private IViewManagerScreenActivity managerScreenActivity;

    @Mock
    private Employee employee;



    private Context context;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        context=new Activity();
        //generate mock object
        employee = new Employee();

        EmployeeAPIService apiService = EmployeeRetrofitInstance.getEmployeeRetrofitInstance().create(EmployeeAPIService.class);


    }

    @Test
    public void start() throws Exception {
    }

    @Test
    public void createPresenter_setsThePresenterToView(){

        // Then the presenter is set to the view
        verify(presenterManagerScreen).jasonCall();
    }
}

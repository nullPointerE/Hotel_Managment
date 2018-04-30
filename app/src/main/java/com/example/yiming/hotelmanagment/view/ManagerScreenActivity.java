package com.example.yiming.hotelmanagment.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.util.adapter.ManagerScreenAdapter;

public class ManagerScreenActivity extends AppCompatActivity implements IViewManagerScreenActivity{
private android.support.v7.widget.Toolbar toolbar;
private RecyclerView employeeRecyclerView;

private IPresenterManagerScreen iPresenterManagerScreen;
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
        iPresenterManagerScreen = new PresenterManagerScreen(this, ManagerScreenActivity.this);
        populateEmployees();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.managerscreen_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeOption:
                finish();
            return true;
            case R.id.addEmployeeOption:
                startActivity(new Intent(this, AddEmployeeActivity.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    public void populateEmployees() {
        iPresenterManagerScreen.jasonCall();
    }

    @Override
    public void setRecyclerViewAdapter(ManagerScreenAdapter managerScreenAdapter) {
        employeeRecyclerView.setAdapter(managerScreenAdapter);
    }


}

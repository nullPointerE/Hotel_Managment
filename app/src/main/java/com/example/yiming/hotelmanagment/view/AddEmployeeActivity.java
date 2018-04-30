package com.example.yiming.hotelmanagment.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.AddEmployee;

public class AddEmployeeActivity extends AppCompatActivity implements IViewAddEmployee, View.OnClickListener {
private EditText name, address, salary, designation, mobileNumber;
private Button submit, cancel;
private AddEmployee addEmployee;
private IPresenterAddEmployee iPresenterAddEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        name = findViewById(R.id.empFname);
        address = findViewById(R.id.empAddress);
        salary = findViewById(R.id.empSalary);
        designation = findViewById(R.id.empDesign);
        mobileNumber = findViewById(R.id.empPhone);
        submit = findViewById(R.id.empSubmitButton);
        submit.setOnClickListener(this);
        cancel = findViewById(R.id.empCancelButton);
        cancel.setOnClickListener(this::onClick);

    }

    @Override
    public void addEmployee() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.empSubmitButton:
                addEmployee = new AddEmployee(name.getText().toString(), address.getText().toString(), salary.getText().toString(), designation.getText().toString(), mobileNumber.getText().toString());
                iPresenterAddEmployee = new PresenterAddEmployee(this, this,addEmployee);
                iPresenterAddEmployee.jasonCall();
                finish();
                break;
            case R.id.empCancelButton:
                finish();
                break;
        }
    }
}

package com.example.yiming.hotelmanagment.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// FIXME generate failure  field _$RoomDetails234
// FIXME generate failure  field _$RoomDetails0
public class Employee {



    @SerializedName("Employee Id")
    @Expose
    private String employeeId;

    @SerializedName("Employee Name")
    @Expose
    private String employeeName;

    @SerializedName("Employee Address")
    @Expose
    private String employeeAddress;

    @SerializedName("Employee Phone")
    @Expose
    private String employeePhone;

    @SerializedName("Employee Salary")
    @Expose
    private String employeeSalary;

    @SerializedName("Employee Designation")
    @Expose
    private String employeeDesignation;

    public Employee(String employeeId, String employeeName, String employeeAddress, String employeePhone, String employeeSalary, String employeeDesignation) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeePhone = employeePhone;
        this.employeeSalary = employeeSalary;
        this.employeeDesignation = employeeDesignation;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }
}

package com.example.yiming.hotelmanagment.common;

public class AddEmployee {
    private String empName, empAdd, empPhone, empSalary,empDesign;

    public AddEmployee(String empName, String empAdd, String empPhone, String empSalary, String empDesign) {
        this.empName = empName;
        this.empAdd = empAdd;
        this.empPhone = empPhone;
        this.empSalary = empSalary;
        this.empDesign = empDesign;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAdd() {
        return empAdd;
    }

    public void setEmpAdd(String empAdd) {
        this.empAdd = empAdd;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(String empSalary) {
        this.empSalary = empSalary;
    }

    public String getEmpDesign() {
        return empDesign;
    }

    public void setEmpDesign(String empDesign) {
        this.empDesign = empDesign;
    }
}

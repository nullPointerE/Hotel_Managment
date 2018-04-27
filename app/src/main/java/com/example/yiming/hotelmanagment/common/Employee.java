package com.example.yiming.hotelmanagment.common;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

// FIXME generate failure  field _$RoomDetails42
// FIXME generate failure  field _$RoomDetails78
// FIXME generate failure  field _$RoomDetails282
// FIXME generate failure  field _$RoomDetails220
// FIXME generate failure  field _$RoomDetails234
// FIXME generate failure  field _$RoomDetails0
public class Employee {

    List<RoomDetailsBean> roomDetailsBeans = new ArrayList<>();
    public void setRoomDetailsBeans(List<RoomDetailsBean> roomDetailsBeans){
        this.roomDetailsBeans = roomDetailsBeans;
    }
    public List<RoomDetailsBean> getRoomDetailsBeans(){
        return roomDetailsBeans;
    }

    public static class RoomDetailsBean {
        @SerializedName("Employee Id")
        private String _$EmployeeId159; // FIXME check this code
        @SerializedName("Employee Name")
        private String _$EmployeeName224; // FIXME check this code
        @SerializedName("Employee Address")
        private String _$EmployeeAddress48; // FIXME check this code
        @SerializedName("Employee Phone")
        private String _$EmployeePhone162; // FIXME check this code
        @SerializedName("Employee Salary")
        private String _$EmployeeSalary273; // FIXME check this code
        @SerializedName("Employee Designation")
        private String _$EmployeeDesignation284; // FIXME check this code

        public String get_$EmployeeId159() {
            return _$EmployeeId159;
        }

        public void set_$EmployeeId159(String _$EmployeeId159) {
            this._$EmployeeId159 = _$EmployeeId159;
        }

        public String get_$EmployeeName224() {
            return _$EmployeeName224;
        }

        public void set_$EmployeeName224(String _$EmployeeName224) {
            this._$EmployeeName224 = _$EmployeeName224;
        }

        public String get_$EmployeeAddress48() {
            return _$EmployeeAddress48;
        }

        public void set_$EmployeeAddress48(String _$EmployeeAddress48) {
            this._$EmployeeAddress48 = _$EmployeeAddress48;
        }

        public String get_$EmployeePhone162() {
            return _$EmployeePhone162;
        }

        public void set_$EmployeePhone162(String _$EmployeePhone162) {
            this._$EmployeePhone162 = _$EmployeePhone162;
        }

        public String get_$EmployeeSalary273() {
            return _$EmployeeSalary273;
        }

        public void set_$EmployeeSalary273(String _$EmployeeSalary273) {
            this._$EmployeeSalary273 = _$EmployeeSalary273;
        }

        public String get_$EmployeeDesignation284() {
            return _$EmployeeDesignation284;
        }

        public void set_$EmployeeDesignation284(String _$EmployeeDesignation284) {
            this._$EmployeeDesignation284 = _$EmployeeDesignation284;
        }
    }
}



package com.example.yiming.hotelmanagment.common;

import java.util.List;

public class Employee {


    private List<RoomdetailsBean> roomdetails;

    public List<RoomdetailsBean> getRoomdetails() {
        return roomdetails;
    }

    public void setRoomdetails(List<RoomdetailsBean> roomdetails) {
        this.roomdetails = roomdetails;
    }

    public static class RoomdetailsBean {
        /**
         * empid : 1
         * empname : hh
         * empaddress : noida
         * empphone : 798787888
         * empsalary : 10000
         * empdesignation : khjhj
         */

        private String empid;
        private String empname;
        private String empaddress;
        private String empphone;
        private String empsalary;
        private String empdesignation;

        public String getEmpid() {
            return empid;
        }

        public void setEmpid(String empid) {
            this.empid = empid;
        }

        public String getEmpname() {
            return empname;
        }

        public void setEmpname(String empname) {
            this.empname = empname;
        }

        public String getEmpaddress() {
            return empaddress;
        }

        public void setEmpaddress(String empaddress) {
            this.empaddress = empaddress;
        }

        public String getEmpphone() {
            return empphone;
        }

        public void setEmpphone(String empphone) {
            this.empphone = empphone;
        }

        public String getEmpsalary() {
            return empsalary;
        }

        public void setEmpsalary(String empsalary) {
            this.empsalary = empsalary;
        }

        public String getEmpdesignation() {
            return empdesignation;
        }

        public void setEmpdesignation(String empdesignation) {
            this.empdesignation = empdesignation;
        }
    }
}



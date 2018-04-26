package com.example.yiming.hotelmanagment.common;

import java.util.Date;

public class Room {
    int roomNumber;
    int status;
    int owedByCustomer;
    double price;
    int beds;

    Date autoCancelDate;
    Date expectCheckInDate;
    Date actualCheckInDate;
    Date expectCheckoOutDate;
    Date actualCheckOutDate;

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOwedByCustomer() {
        return owedByCustomer;
    }

    public void setOwedByCustomer(int owedByCustomer) {
        this.owedByCustomer = owedByCustomer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public Date getAutoCancelDate() {
        return autoCancelDate;
    }

    public void setAutoCancelDate(Date autoCancelDate) {
        this.autoCancelDate = autoCancelDate;
    }

    public Date getExpectCheckInDate() {
        return expectCheckInDate;
    }

    public void setExpectCheckInDate(Date expectCheckInDate) {
        this.expectCheckInDate = expectCheckInDate;
    }

    public Date getActualCheckInDate() {
        return actualCheckInDate;
    }

    public void setActualCheckInDate(Date actualCheckInDate) {
        this.actualCheckInDate = actualCheckInDate;
    }

    public Date getExpectCheckoOutDate() {
        return expectCheckoOutDate;
    }

    public void setExpectCheckoOutDate(Date expectCheckoOutDate) {
        this.expectCheckoOutDate = expectCheckoOutDate;
    }

    public Date getActualCheckOutDate() {
        return actualCheckOutDate;
    }

    public void setActualCheckOutDate(Date actualCheckOutDate) {
        this.actualCheckOutDate = actualCheckOutDate;
    }

}

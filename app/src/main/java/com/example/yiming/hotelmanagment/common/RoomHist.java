package com.example.yiming.hotelmanagment.common;

import java.util.Date;

public class RoomHist {
    int transactionId;
    int roomNumber;
    double totalPrice;
    int status;
    int owedByCustomer;

    Date autoCancelDate;
    Date expectCheckInDate;
    Date actualCheckInDate;
    Date expectCheckoOutDate;
    Date actualCheckOutDate;


    public int getTransactionId() {
        return transactionId;
    }

    public int isCheckedIn(){
        return status;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

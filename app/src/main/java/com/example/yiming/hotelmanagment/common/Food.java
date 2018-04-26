package com.example.yiming.hotelmanagment.common;

public class Food {
    int foodId;
    String foodType;
    String foodName;
    Double price;
    int isPayed;



    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int isPayed() {
        return isPayed;
    }

    public void setPayed(int payed) {
        isPayed = payed;
    }

}

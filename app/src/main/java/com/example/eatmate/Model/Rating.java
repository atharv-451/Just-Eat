package com.example.eatmate.Model;

public class Rating {
    private String userPhone;
    private String foodId;
    private String rateView;
    private String comment;

    public Rating() {
    }

    public Rating(String userPhone, String foodId, String rateView, String comment) {
        this.userPhone = userPhone;
        this.foodId = foodId;
        this.rateView = rateView;
        this.comment = comment;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getRateView() {
        return rateView;
    }

    public void setRateView(String rateView) {
        this.rateView = rateView;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

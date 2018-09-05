package com.dev.shop.requestmodels;

public class DiscountModel {
    public double percent;

    public double value;

    public DiscountModel() {}

    public DiscountModel(double percent, double value){
        this.percent = percent;
        this.value = value;
    }
}

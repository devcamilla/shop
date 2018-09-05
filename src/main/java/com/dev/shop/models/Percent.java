package com.dev.shop.models;

import javax.persistence.Column;

public class Percent {
    @Column(name = "percent_value")
    private double value;

    public Percent() {
        this(0d);
    }

    public Percent(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

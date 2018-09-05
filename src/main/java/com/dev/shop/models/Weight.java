package com.dev.shop.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Weight {
    @Column(name = "weight_value")
    private double value;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight_value")
    private WeightUnit unit;

    public Weight() {
        this(0d, WeightUnit.G);
    }

    public Weight(double value, WeightUnit unit){
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }
}

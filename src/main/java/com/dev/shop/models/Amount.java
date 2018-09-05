package com.dev.shop.models;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Amount {
    @Column(name = "amount_value")
    private double value;

    public Amount() {
        this(0d);
    }

    public Amount(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

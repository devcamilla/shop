package com.dev.shop.models;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Discount {
    @Embedded
    private Percent percent;

    @Embedded
    private Amount value;

    protected Discount() {}

    public Discount(Percent percent) {
        this(percent, new Amount());
    }

    public Discount(Amount value){
        this(new Percent(), value);
    }

    public Discount(Percent percent, Amount value){
        this.percent = percent;
        this.value = value;
    }

    public Percent getPercent() {
        return percent;
    }

    public Amount getValue() {
        return value;
    }

    public Amount applyTo(Amount amount) {
        double discount = Math.max(percent.applyTo(amount.getValue()),
                Math.min(value.getValue(), amount.getValue()));
        return new Amount(discount);
    }
}

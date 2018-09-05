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

    public Discount(Percent percent){
        this.percent = percent;
    }

    public Discount(Amount value){
        this.value = value;
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
}

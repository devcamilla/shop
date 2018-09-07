package com.dev.shop.models;

import javax.persistence.Embeddable;

@Embeddable
public class ItemType {
    private String value;

    protected ItemType() {}

    public ItemType(String type){
        this.value = type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemType){
            return ((ItemType) obj).value.equalsIgnoreCase(value);
        }
        return false;
    }
}
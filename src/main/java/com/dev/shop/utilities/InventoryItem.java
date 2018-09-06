package com.dev.shop.utilities;

import com.dev.shop.models.Amount;

public class InventoryItem {

    private String code;

    private Amount unitPrice;

    public InventoryItem(String code, Amount unitPrice){
        this.code = code;
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }

    public Amount getUnitPrice() {
        return unitPrice;
    }
}

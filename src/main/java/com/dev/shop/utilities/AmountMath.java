package com.dev.shop.utilities;

import com.dev.shop.models.Amount;

public class AmountMath {
    public static Amount substract(Amount left, Amount right){
        return new Amount(left.getValue() - right.getValue());
    }
}

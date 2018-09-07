package com.dev.shop.utilities;

import com.dev.shop.models.ItemType;
import com.dev.shop.models.Percent;

public class SimpleCouponRegistry extends CouponRegistry {
    public void addCoupon(String code, Percent percent){
        coupons.add(new Coupon(code, percent));
    }

    public void addCoupon(String code, Percent percent, ItemType itemType){
        coupons.add(new Coupon(code, percent, itemType));
    }
}

package com.dev.shop.utilities;

import com.dev.shop.models.Item;
import com.dev.shop.models.ItemType;
import com.dev.shop.models.Percent;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CouponRegistry {
    protected List<Coupon> coupons = new ArrayList<>();

    public Coupon getCoupon(String code){
        return coupons.stream()
                .filter(coupon -> coupon.getCode().equalsIgnoreCase(code))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public class Coupon {
        private String code;

        private Percent percent;

        private ItemType itemType;

        public Coupon(String code, Percent percent){
            this.code = code;
            this.percent = percent;
        }

        public Coupon(String code, Percent percent, ItemType itemType){
            this.code = code;
            this.percent = percent;
            this.itemType = itemType;
        }

        public String getCode() {
            return code;
        }

        public Percent getPercent() {
            return percent;
        }

        public ItemType getItemType() {
            return itemType;
        }

        public boolean appliesTo(ItemType itemType){
            if (this.itemType == null) return true;
            return this.itemType.equals(itemType);
        }
    }
}

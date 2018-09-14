package com.dev.shop.utilities;

import com.dev.shop.models.Amount;
import com.dev.shop.models.Cart;
import com.dev.shop.models.Item;
import com.dev.shop.repositories.ItemRepository;

import java.util.Optional;

public class Cashier {
    private CouponRegistry couponRegistry;

    private ItemRepository itemRepository;

    public Cashier(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Cashier(ItemRepository itemRepository, CouponRegistry couponRegistry) {
        this.itemRepository = itemRepository;
        this.couponRegistry = couponRegistry;
    }

    public Amount checkout(Cart cart) {
        return checkout(cart, Optional.empty());
    }

    public Amount checkout(Cart cart, String couponCode) {
        Optional<CouponRegistry.Coupon> coupon = couponRegistry.getCoupon(couponCode);
        return checkout(cart, coupon);
    }

    private Amount checkout(Cart cart, Optional<CouponRegistry.Coupon> coupon) {
        double totalCost = cart.getCartItems().stream()
                .mapToDouble(cartItem -> {
                    Item item = itemRepository.findByCode(cartItem.getItemCode());
                    double cost = item.getNetPrice().getValue() * cartItem.getQuantity();

                    if (!coupon.isPresent()) return cost;

                    if (!coupon.get().appliesTo(item.getType())) return cost;

                    double discount = coupon.get().getPercent().applyTo(cost);
                    return cost - discount;
                }).sum();

        return new Amount(totalCost);
    }
}

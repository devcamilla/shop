package com.dev.shop.utilities;

import com.dev.shop.models.*;
import com.dev.shop.repositories.InMemoryItemRepository;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CashierTest {
    @Test
    public void checkout_ShouldReturnTotalCost(){
        InMemoryItemRepository itemRepository = new InMemoryItemRepository();
        itemRepository.addItem("Apple", new ItemType("VEG"), new Amount(10), new Weight(10, WeightUnit.G));

        Cart cart = new Cart();
        cart.addItem("Apple", 10);

        Cashier cashier = new Cashier(itemRepository);
        Amount totalCost = cashier.checkout(cart);

        assertEquals(100d, totalCost.getValue());
    }

    @Test
    public void checkout_ShouldReturnTotalCost_UseItemPercentDiscount(){
        InMemoryItemRepository itemRepository = new InMemoryItemRepository();
        itemRepository.addItem("Apple", new ItemType("VEG"), new Amount(10), new Weight(10, WeightUnit.G), new Discount(new Percent(10)));

        Cart cart = new Cart();
        cart.addItem("Apple", 10);

        Cashier cashier = new Cashier(itemRepository);
        Amount totalCost = cashier.checkout(cart);

        assertEquals(90d, totalCost.getValue());
    }

    @Test
    public void checkout_ShouldReturnTotalCost_UseItemValueDiscount(){
        InMemoryItemRepository itemRepository = new InMemoryItemRepository();
        itemRepository.addItem("Apple", new ItemType("VEG"), new Amount(10), new Weight(10, WeightUnit.G), new Discount(new Amount(5)));

        Cart cart = new Cart();
        cart.addItem("Apple", 10);

        Cashier cashier = new Cashier(itemRepository);
        Amount totalCost = cashier.checkout(cart);

        assertEquals(50d, totalCost.getValue());
    }

    @Test
    public void checkout_ShouldReturnTotalCost_UsingStoreWideCoupon(){
        InMemoryItemRepository itemRepository = new InMemoryItemRepository();
        itemRepository.addItem("Apple", new ItemType("VEG"), new Amount(10), new Weight(10, WeightUnit.G));

        Cart cart = new Cart();
        cart.addItem("Apple", 10);

        SimpleCouponRegistry couponRegistry = new SimpleCouponRegistry();
        couponRegistry.addCoupon("ALL10%OFF", new Percent(10));

        Cashier cashier = new Cashier(itemRepository, couponRegistry);
        Amount totalCost = cashier.checkout(cart, "ALL10%OFF");

        assertEquals(90d, totalCost.getValue());
    }

    @Test
    public void checkout_ShouldReturnTotalCost_UsingItemTypeCoupon(){
        InMemoryItemRepository itemRepository = new InMemoryItemRepository();
        itemRepository.addItem("Apple", new ItemType("FRUIT"), new Amount(10), new Weight(10, WeightUnit.G));

        Cart cart = new Cart();
        cart.addItem("Apple", 10);

        SimpleCouponRegistry couponRegistry = new SimpleCouponRegistry();
        couponRegistry.addCoupon("FRUIT10%OFF", new Percent(10), new ItemType("FRUIT"));

        Cashier cashier = new Cashier(itemRepository, couponRegistry);
        Amount totalCost = cashier.checkout(cart, "FRUIT10%OFF");

        assertEquals(90d, totalCost.getValue());
    }

    @Test
    public void checkout_ShouldReturnTotalCost_UsingItemTypeCoupon_NotApplicableItemType(){
        InMemoryItemRepository itemRepository = new InMemoryItemRepository();
        itemRepository.addItem("Squash", new ItemType("VEG"), new Amount(10), new Weight(10, WeightUnit.G));

        Cart cart = new Cart();
        cart.addItem("Squash", 10);

        SimpleCouponRegistry couponRegistry = new SimpleCouponRegistry();
        couponRegistry.addCoupon("FRUIT10%OFF", new Percent(10), new ItemType("FRUIT"));

        Cashier cashier = new Cashier(itemRepository, couponRegistry);
        Amount totalCost = cashier.checkout(cart, "FRUIT10%OFF");

        assertEquals(100d, totalCost.getValue());
    }
}

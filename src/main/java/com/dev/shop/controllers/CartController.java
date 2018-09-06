package com.dev.shop.controllers;

import com.dev.shop.assemblers.CartAssembler;
import com.dev.shop.dtos.CartInfo;
import com.dev.shop.dtos.CartItemInfo;
import com.dev.shop.models.Amount;
import com.dev.shop.models.Cart;
import com.dev.shop.repositories.CartRepository;
import com.dev.shop.repositories.ItemRepository;
import com.dev.shop.requestmodels.CartModel;
import com.dev.shop.services.CartService;
import com.dev.shop.utilities.Cashier;
import com.dev.shop.utilities.ShopInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<CartModel> getAll(){
        return cartRepository.findAll().stream()
                .map(cart -> CartAssembler.toCartModel(cart))
                .collect(Collectors.toList());
    }

    @PostMapping
    public long create(@RequestBody CartModel cartModel){
        CartInfo cartInfo = CartAssembler.toCartInfo(cartModel);
        Cart cart = cartService.create(cartInfo);
        return cart.getId();
    }

    @GetMapping("/{id}")
    public CartModel getById(@PathVariable long id){
        return cartRepository.findById(id)
                .map(cart -> CartAssembler.toCartModel(cart))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @PostMapping("/{id}/add")
    public CartModel addCartItem(@PathVariable long id, @RequestParam String itemCode, @RequestParam double quantity){
        Cart cart = cartService.addCartItem(id, new CartItemInfo(itemCode, quantity));
        return CartAssembler.toCartModel(cart);
    }

    @PostMapping("/{id}/remove")
    public CartModel addCartItem(@PathVariable long id, @RequestParam String itemCode){
        Cart cart = cartService.removeCartItem(id, itemCode);
        return CartAssembler.toCartModel(cart);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){
        cartRepository.deleteById(id);
    }

    @GetMapping("/{id}/checkout")
    public double checkout(@PathVariable long id){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        ShopInventory shopInventory = new ShopInventory(itemRepository);
        Cashier cashier = new Cashier(shopInventory);

        Amount totalCost = cashier.checkout(cart);
        return totalCost.getValue();
    }
}

package com.dev.shop.controllers;

import com.dev.shop.assemblers.CartAssembler;
import com.dev.shop.models.Amount;
import com.dev.shop.models.Cart;
import com.dev.shop.repositories.CartRepository;
import com.dev.shop.repositories.JpaItemRepository;
import com.dev.shop.requestmodels.CartItemModel;
import com.dev.shop.requestmodels.CartModel;
import com.dev.shop.services.CartService;
import com.dev.shop.utilities.Cashier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private JpaItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity getAll(){
        List<CartModel> cartModels = cartRepository.findAll().stream()
                .map(cart -> CartAssembler.toCartModel(cart))
                .collect(Collectors.toList());

        return ResponseEntity.ok(cartModels);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CartModel cartModel){
        Cart cart = cartService.create();
        long id = cart.getId();
        for (CartItemModel cartItemModel: cartModel.cartItemModels) {
            cartService.addItem(id, cartItemModel.itemCode, cartItemModel.quantity);
        }
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable long id){
        Optional<CartModel> cartModel = cartRepository.findById(id)
                .map(cart -> CartAssembler.toCartModel(cart));

        if (!cartModel.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartModel);
    }

    @PostMapping("/{id}/add")
    public ResponseEntity addCartItem(@PathVariable long id, @RequestParam String itemCode, @RequestParam double quantity){
        Cart cart = cartService.addItem(id, itemCode, quantity);
        return ResponseEntity.ok(CartAssembler.toCartModel(cart));
    }

    @PostMapping("/{id}/remove")
    public ResponseEntity addCartItem(@PathVariable long id, @RequestParam String itemCode){
        Cart cart = cartService.removeItem(id, itemCode);
        return ResponseEntity.ok(CartAssembler.toCartModel(cart));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){
        cartRepository.deleteById(id);
    }

    @GetMapping("/{id}/checkout")
    public ResponseEntity checkout(@PathVariable long id){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        Cashier cashier = new Cashier(itemRepository);

        Amount totalCost = cashier.checkout(cart);
        return ResponseEntity.ok(totalCost.getValue());
    }
}

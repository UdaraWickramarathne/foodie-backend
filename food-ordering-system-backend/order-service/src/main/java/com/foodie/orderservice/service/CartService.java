package com.foodie.orderservice.service;

import com.foodie.orderservice.model.Cart;
import com.foodie.orderservice.model.CartProduct;
import com.foodie.orderservice.repository.CartProductRepository;
import com.foodie.orderservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductRepository cartProductRepository;


    public Cart addToCart(int customerId, int productId, int quantity, double productPrice) {

        Cart cart = cartRepository.findByUserId(customerId);
        if(cart == null) {
            cart = new Cart(customerId);
            cartRepository.save(cart);
        }

        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), productId);

        if(cartProduct == null) {
            cartProduct = new CartProduct(productId, quantity, productPrice, cart);
            cart.getCartProducts().add(cartProduct);
        } else {
            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
        }

        cartProductRepository.save(cartProduct);

        cart.setTotalAmount(cart.getTotalAmount() + (quantity * productPrice));
        return cartRepository.save(cart);



    }

    public Cart getCart(int customerId) {
        Cart cart = cartRepository.findByUserId(customerId);
        if(cart == null) {
            cart = new Cart(customerId);
            cartRepository.save(cart);
        }
        return cart;
    }


    public ResponseEntity<Cart> removeFromCart(int customerId, int productId) {
        Cart cart = cartRepository.findByUserId(customerId);
        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), productId);
        if(cartProduct != null) {
            cart.setTotalAmount(cart.getTotalAmount() - cartProduct.getQuantity() * cartProduct.getProductPrice());
            cartProductRepository.delete(cartProduct);
            return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public Cart updateProductQuantity(int customerId, int productId, int quantity) {
        Cart cart = cartRepository.findByUserId(customerId);
        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), productId);
        if(cartProduct != null) {
            cart.setTotalAmount(cart.getTotalAmount() - cartProduct.getQuantity() * cartProduct.getProductPrice());
            cartProduct.setQuantity(quantity);
            cartProductRepository.save(cartProduct);
            cart.setTotalAmount(cart.getTotalAmount() + cartProduct.getQuantity() * cartProduct.getProductPrice());
            return cartRepository.save(cart);
        }
        return null;
    }

    public Cart updateProductIsSelected(int customerId, int productId, boolean isSelected) {
        Cart cart = cartRepository.findByUserId(customerId);
        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), productId);
        if(cartProduct != null) {
            cartProduct.setSelected(isSelected);
            cartProductRepository.save(cartProduct);
            return cartRepository.save(cart);
        }
        return null;
    }
}

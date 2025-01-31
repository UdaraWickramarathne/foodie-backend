package com.foodie.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productId;
    private double productPrice;
    private int quantity;
    private boolean isSelected;

    @ManyToOne
    @JsonIgnore
    private Cart cart;


    public CartProduct(int productId, int quantity, double productPrice,  Cart cart) {
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.cart = cart;
        this.isSelected = true;
    }
}

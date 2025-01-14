package com.foodie.orderservice.model;



import jakarta.persistence.*;
import lombok.*;


import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int cartId;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String address;
    private String paymentMode;
    private Date createdAt;

}

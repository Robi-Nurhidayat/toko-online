package com.pgwaktupagi.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CartDTO {

    private Long id;
    private Long userId;
    private Date createdAt;
    private Date updatedAt;
    private List<CartItemDTO> cartItems;
    private int totalQuantity;
    private double totalPrice;
}

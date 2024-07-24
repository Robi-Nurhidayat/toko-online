package com.pgwaktupagi.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long id;
    private Long productId;
    private int quantity;
    private Double price;
}

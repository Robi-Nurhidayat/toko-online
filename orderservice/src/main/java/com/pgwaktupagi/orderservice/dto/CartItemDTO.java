package com.pgwaktupagi.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
    private Long cartId;
    private String productId;
    private int quantity;
    private Double price;
}

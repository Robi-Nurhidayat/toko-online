package com.pgwaktupagi.cartservice.service;

import com.pgwaktupagi.cartservice.dto.CartItemDTO;

public interface ICartItemService {


    CartItemDTO addToCart(CartItemDTO cartItemDTO);

    boolean deleteCart(Long id);
}

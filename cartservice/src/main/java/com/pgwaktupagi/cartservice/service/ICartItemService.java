package com.pgwaktupagi.cartservice.service;

import com.pgwaktupagi.cartservice.dto.CartItemDTO;

public interface ICartItemService {


    CartItemDTO addToCartItem(CartItemDTO cartItemDTO);

    CartItemDTO updateCartItem(CartItemDTO cartItemDTO);

    boolean deleteCart(Long id);
}

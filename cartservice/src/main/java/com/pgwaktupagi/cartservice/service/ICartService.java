package com.pgwaktupagi.cartservice.service;

import com.pgwaktupagi.cartservice.dto.CartDTO;

import java.util.List;

public interface ICartService {

    List<CartDTO> getAllCart();

    CartDTO findCartById(Long id);
    boolean deleteCart(Long id);
}

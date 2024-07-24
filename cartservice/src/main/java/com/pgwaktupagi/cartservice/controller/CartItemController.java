package com.pgwaktupagi.cartservice.controller;

import com.pgwaktupagi.cartservice.dto.CartItemDTO;
import com.pgwaktupagi.cartservice.dto.Response;
import com.pgwaktupagi.cartservice.service.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final ICartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<Response> addItemToCart(@RequestBody CartItemDTO cartItemDTO) {

        CartItemDTO cartItem = cartItemService.addToCart(cartItemDTO);



        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("201","Success add to cart",cartItem));
    }
}

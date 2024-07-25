package com.pgwaktupagi.cartservice.controller;

import com.pgwaktupagi.cartservice.dto.CartItemDTO;
import com.pgwaktupagi.cartservice.dto.Response;
import com.pgwaktupagi.cartservice.service.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final ICartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<Response> addItemToCart(@RequestBody CartItemDTO cartItemDTO) {

        CartItemDTO cartItem = cartItemService.addToCartItem(cartItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("201","Success add to cart",cartItem));
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateCartItem(@RequestBody CartItemDTO cartItemDTO) {

        CartItemDTO cartItem = cartItemService.updateCartItem(cartItemDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Success update cart",cartItem));
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteCartItem(@RequestParam("id") Long id) {

        boolean isDelete = cartItemService.deleteCart(id);

        if (isDelete) {
            return ResponseEntity.status(HttpStatus.OK).body(new Response("200","deleting cart item success",null));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("400","deleting cart item failed",null));
    }
}

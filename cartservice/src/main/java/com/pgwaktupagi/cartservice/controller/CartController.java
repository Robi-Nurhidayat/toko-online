package com.pgwaktupagi.cartservice.controller;

import com.pgwaktupagi.cartservice.dto.CartDTO;
import com.pgwaktupagi.cartservice.dto.Response;
import com.pgwaktupagi.cartservice.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @GetMapping
    public ResponseEntity<Response> getAllData() {

        List<CartDTO> allCart = cartService.getAllCart();
        return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Sukses get all data", allCart));

    }

    @DeleteMapping
    public ResponseEntity<Response> delete(@RequestParam Long id){

        boolean isDeleted = cartService.deleteCart(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Success delete cart",null));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("400","failed delete cart",null));
    }
}
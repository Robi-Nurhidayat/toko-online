package com.pgwaktupagi.cartservice.controller;

import com.pgwaktupagi.cartservice.dto.CartDTO;
import com.pgwaktupagi.cartservice.dto.CartInfo;
import com.pgwaktupagi.cartservice.dto.Response;
import com.pgwaktupagi.cartservice.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    private final ICartService cartService;
    private final CartInfo cartInfo;

    @GetMapping
    public ResponseEntity<Response> getAllData() {

        List<CartDTO> allCart = cartService.getAllCart();
        return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Sukses get all data", allCart));

    }

    @DeleteMapping
    public ResponseEntity<Response> delete(@RequestParam("cartId") Long cartId){

        boolean isDeleted = cartService.deleteCart(cartId);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Success delete cart",null));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("400","failed delete cart",null));
    }

    @GetMapping("/find")
    public ResponseEntity<Response> findCartById(@RequestParam("cartId") Long cartId) {
        CartDTO cartById = cartService.findCartById(cartId);

        return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Success",cartById));
    }

    @GetMapping("/find-by-user-id")
    public ResponseEntity<Response> findCartByUserId(@RequestParam("userId") Long userId) {
        CartDTO cartByUserId = cartService.findCartByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Success",cartByUserId));
    }

    @GetMapping("/cart-info")
    public ResponseEntity<CartInfo> getInfo() {

        log.info("invoked");

        throw new RuntimeException();
//        return new ResponseEntity<>(cartInfo,HttpStatus.OK);
    }


}
package com.pgwaktupagi.orderservice.service.client;

import com.pgwaktupagi.orderservice.dto.ResponseCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "carts", url = "http://localhost:8002/api/carts")
public interface CartClient {

    @GetMapping("/find-by-user-id")
    public ResponseEntity<ResponseCart> findCartByUserId(@RequestParam("userId") Long userId);
    @DeleteMapping
    public ResponseEntity<ResponseCart> delete(@RequestParam Long id);

}

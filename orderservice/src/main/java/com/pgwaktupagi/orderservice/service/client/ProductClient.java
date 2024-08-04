package com.pgwaktupagi.orderservice.service.client;

import com.pgwaktupagi.orderservice.dto.ResponseProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "products", url = "http://localhost:8000/api/product")
public interface ProductClient {
    @GetMapping("/find-by-id")
    public ResponseEntity<ResponseProduct> findbyId(@RequestParam("productId") String productId);
}
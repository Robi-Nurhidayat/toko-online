package com.pgwaktupagi.cartservice.service.client;

import com.pgwaktupagi.cartservice.dto.ResponseProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductFallback implements ProductClient{
    @Override
    public ResponseEntity<ResponseProduct> findbyId(String productId) {
        return null;
    }
}

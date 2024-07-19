package com.pgwaktupagi.productservice.utils;

import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.dto.ResponseProduct;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomeError {

    public static ResponseEntity<ResponseProduct> ValidationErrorFieldCustome(ProductDTO productDTO) {
        Map<String, String> validationErrors = new HashMap<>();

        if (productDTO.getName() == null || productDTO.getName().isBlank()) {
            validationErrors.put("name", "Name is required");
        }
        if (productDTO.getPrice() == null) {
            validationErrors.put("price", "Price is required");
        }
        if (productDTO.getStock() == null) {
            validationErrors.put("stock", "Stock is required");
        }
        if (productDTO.getDescription() == null || productDTO.getDescription().isBlank()) {
            validationErrors.put("description", "Description is required");
        }
        if (productDTO.getCategory() == null || productDTO.getCategory().isBlank()) {
            validationErrors.put("category", "Category is required");
        }

        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseProduct("400", "Validation failed", validationErrors));
        }

        return null;
    }
}

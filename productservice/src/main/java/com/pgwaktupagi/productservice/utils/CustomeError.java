package com.pgwaktupagi.productservice.utils;

import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.dto.ResponseProduct;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomeError {

    public static ResponseEntity<ResponseProduct> ValidationErrorFieldCustome(ProductDTO productDTO) {
        Map<String, String> validationErrors = new HashMap<>();

        // validation for product name
        if (productDTO.getName() == null || productDTO.getName().isBlank()) {
            validationErrors.put("name", "Name is required");
        } else if (productDTO.getName().length() <= 3) {
            validationErrors.put("name","Product name must be greather than 3 characters");
        }else if (productDTO.getName() instanceof String != true){
            validationErrors.put("name","Invalid format product name, please enter correct product name");
        }else if (validationStringToInteger(productDTO.getName())) {
            validationErrors.put("name","Invalid format product name, product name must be start with alpabet not number");
        }

        // validation for price
        if (productDTO.getPrice() == null) {
            validationErrors.put("price", "Price is required");
        } else if (productDTO.getPrice() < 0) {
            validationErrors.put("price","Product price must be greather than 0");
        }

        // validation for stock
        if (productDTO.getStock() == null) {
            validationErrors.put("stock", "Price is required");
        } else if (productDTO.getStock() < 0) {
            validationErrors.put("price","Product price must be greather than 0");
        }
//        if (productDTO.getDescription() == null || productDTO.getDescription().isBlank()) {
//            validationErrors.put("description", "Description is required");
//        }
        if (productDTO.getCategory() == null || productDTO.getCategory().isBlank()) {
            validationErrors.put("category", "Category is required");
        }

        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseProduct("400", "Validation failed", validationErrors));
        }

        return null;
    }

    // metode ini digunakan untuk check apakah product name di input dengan tipe data number
    private static boolean validationStringToInteger(String productName) {

        String regex = "^[0-9].*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(productName);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}

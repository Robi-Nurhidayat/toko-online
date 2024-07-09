package com.pgwaktupagi.productservice.controller;

import com.pgwaktupagi.productservice.dto.ResponseProduct;
import com.pgwaktupagi.productservice.entity.Product;
import com.pgwaktupagi.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/product")
    public ResponseEntity<ResponseProduct> getAllProduct() {

        List<Product> productList = productService.getAllProduct();
        ResponseProduct responseProduct = new ResponseProduct();
        responseProduct.setStatusCode(HttpStatus.OK.toString());
        responseProduct.setMessage("Sukses get all data");
        responseProduct.setData(productList);
        return ResponseEntity.status(HttpStatus.OK).body(responseProduct);
    }
}

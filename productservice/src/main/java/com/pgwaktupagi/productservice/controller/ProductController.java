package com.pgwaktupagi.productservice.controller;

import com.pgwaktupagi.productservice.constant.ProductConstants;
import com.pgwaktupagi.productservice.dto.ResponseProduct;
import com.pgwaktupagi.productservice.entity.Product;
import com.pgwaktupagi.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/product")
    public ResponseEntity<ResponseProduct> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseProduct(ProductConstants.STATUS_201,ProductConstants.MESSAGE_201,newProduct));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ResponseProduct> deleteProduct(@PathVariable("productId") String productId){
        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseProduct(HttpStatus.OK.toString(),"Success delete product", ""));
    }
}

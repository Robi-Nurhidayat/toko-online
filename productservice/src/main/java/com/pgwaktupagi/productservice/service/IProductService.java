package com.pgwaktupagi.productservice.service;

import com.pgwaktupagi.productservice.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProduct();

    Product createProduct(Product product);

    Product updateProduct(String productId);

    void deleteProduct(String productId);

}

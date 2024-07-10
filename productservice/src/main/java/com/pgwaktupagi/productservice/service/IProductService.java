package com.pgwaktupagi.productservice.service;

import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProduct();

    ProductDTO fetchProduct(String name);

    Product createProduct(Product product);

    Product updateProduct(String productId);

    void deleteProduct(String productId);

}

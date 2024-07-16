package com.pgwaktupagi.productservice.service;

import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {

    List<Product> getAllProduct();

    ProductDTO fetchProduct(String name);

    Product createProduct(Product product, MultipartFile image)  throws IOException;;

    Product updateProduct(ProductDTO productDTO);

    boolean deleteProduct(String productId);

}
